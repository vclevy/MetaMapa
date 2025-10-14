package ar.utn.ba.ddsi.cliente_liviano.services.impl;

import ar.utn.ba.ddsi.cliente_liviano.models.ResultadoEstadisticaDTO;
import ar.utn.ba.ddsi.cliente_liviano.models.dashboard.DashboardStats;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DashboardBuilderService {

    // "Hora del día con mayor cantidad de hechos en categoría X"
    private static final Pattern CAT_IN_HOUR =
            Pattern.compile("hora del d[ií]a.*categor[ií]a\\s+([^:.,;]+)\\s*.*", Pattern.CASE_INSENSITIVE);

    // "Provincia con más hechos de la categoría X" o "… en categoría X"
    private static final Pattern CAT_IN_PROV =
            Pattern.compile("provincia con m[aá]s hechos.*categor[ií]a\\s+([^:.,;]+)\\s*.*", Pattern.CASE_INSENSITIVE);

    private static String fold(String s){
        if (s == null) return null;
        String n = Normalizer.normalize(s, Normalizer.Form.NFD);
        return n.replaceAll("\\p{M}", "").toLowerCase(Locale.ROOT).trim();
    }

    private static String extractCategoria(String titulo){
        if (titulo == null) return null;
        String t = fold(titulo);
        Matcher m1 = CAT_IN_HOUR.matcher(t);
        if (m1.find()) return m1.group(1).trim();
        Matcher m2 = CAT_IN_PROV.matcher(t);
        if (m2.find()) return m2.group(1).trim();
        return null;
    }

    public DashboardStats build(List<ResultadoEstadisticaDTO> datos){
        DashboardStats vm = new DashboardStats();
        if (datos == null || datos.isEmpty()) return vm;

        // timestamp más reciente
        vm.setGenerado(datos.stream()
                .filter(Objects::nonNull)
                .map(ResultadoEstadisticaDTO::getFechaGeneracion)
                .filter(Objects::nonNull)
                .max(LocalDateTime::compareTo)
                .orElse(null));

        // Comparador: primero por valor, luego por fecha
        Comparator<ResultadoEstadisticaDTO> byValorLuegoFecha = Comparator
                .comparingLong((ResultadoEstadisticaDTO d) -> Optional.ofNullable(d.getValor()).orElse(0L))
                .thenComparing(ResultadoEstadisticaDTO::getFechaGeneracion,
                        Comparator.nullsLast(LocalDateTime::compareTo));

        // Mapas agrupados
        Map<String, ResultadoEstadisticaDTO> bestHourByCat = new LinkedHashMap<>();
        Map<String, ResultadoEstadisticaDTO> bestProvByCat = new LinkedHashMap<>();
        Map<String, ResultadoEstadisticaDTO> bestProvByColeccion = new LinkedHashMap<>();

        for (ResultadoEstadisticaDTO r : datos){
            if (r == null) continue;

            String tituloFold = fold(r.getNombreEstadistica());
            if (tituloFold == null) continue;

            // 1) Categoria con mas hechos (global)
            if (tituloFold.startsWith("categoria con mas hechos")){
                vm.setCategoriaConMasHechos(
                        new DashboardStats.CategoryTop(r.getClave(), Optional.ofNullable(r.getValor()).orElse(0L)));
                continue;
            }

            // 2) Provincia con más hechos (GLOBAL)
            if (tituloFold.equals("provincia con mas hechos") && r.getNombreDeLaColeccion() == null){
                vm.setProvinciaConMasHechos(
                        new DashboardStats.ProvinceTop(r.getClave(), Optional.ofNullable(r.getValor()).orElse(0L)));
                continue;
            }

            // 2.b) Provincia con más hechos POR COLECCIÓN
            if (tituloFold.equals("provincia con mas hechos") && r.getNombreDeLaColeccion() != null){
                String coleccionKey = fold(r.getNombreDeLaColeccion());
                bestProvByColeccion.merge(coleccionKey, r,
                        (a,b) -> byValorLuegoFecha.compare(a,b) >= 0 ? a : b);
                continue;
            }

            // 3) Hora top por categoría
            if (tituloFold.startsWith("hora del dia")){
                String catKeyFold = Optional.ofNullable(extractCategoria(r.getNombreEstadistica()))
                        .map(DashboardBuilderService::fold)
                        .orElseGet(() -> fold(r.getNombreDeLaColeccion()));
                if (catKeyFold != null){
                    bestHourByCat.merge(catKeyFold, r,
                            (a,b) -> byValorLuegoFecha.compare(a,b) >= 0 ? a : b);
                }
                continue;
            }

            // 4) Provincia top por categoría
            if (tituloFold.startsWith("provincia con mas hechos de la categoria")
                    || tituloFold.startsWith("provincia con mas hechos en categoria")){
                String catKeyFold = Optional.ofNullable(extractCategoria(r.getNombreEstadistica()))
                        .map(DashboardBuilderService::fold)
                        .orElseGet(() -> fold(r.getNombreDeLaColeccion()));
                if (catKeyFold != null){
                    bestProvByCat.merge(catKeyFold, r,
                            (a,b) -> byValorLuegoFecha.compare(a,b) >= 0 ? a : b);
                }
            }
        }

        // === Volcar datos al ViewModel ===

        // Horas por categoría
        bestHourByCat.forEach((catFold, r) -> {
            String catLabel = Optional.ofNullable(extractCategoria(r.getNombreEstadistica()))
                    .orElse(Objects.toString(r.getNombreDeLaColeccion(), catFold));

            vm.getHoraTopPorCategoria().add(
                    new DashboardStats.HourTopByCategory(
                            catLabel,
                            r.getClave(),
                            Optional.ofNullable(r.getValor()).orElse(0L)
                    )
            );
        });

        // Provincias por categoría
        bestProvByCat.forEach((catFold, r) -> {
            String catLabel = Optional.ofNullable(extractCategoria(r.getNombreEstadistica()))
                    .orElse(Objects.toString(r.getNombreDeLaColeccion(), catFold));

            vm.getProvinciaTopPorCategoria().put(
                    catLabel,
                    new DashboardStats.ProvinceTop(
                            r.getClave(),
                            Optional.ofNullable(r.getValor()).orElse(0L)
                    )
            );
        });

        // 🆕 Provincias por colección
        bestProvByColeccion.forEach((coleccionFold, r) -> {
            String coleccionLabel = Optional.ofNullable(r.getNombreDeLaColeccion()).orElse(coleccionFold);
            vm.getProvinciaTopPorColeccion().put(
                    coleccionLabel,
                    new DashboardStats.ProvinceTop(
                            r.getClave(),
                            Optional.ofNullable(r.getValor()).orElse(0L)
                    )
            );
        });

        return vm;
    }
}