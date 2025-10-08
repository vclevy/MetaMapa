package ar.utn.ba.ddsi.cliente_liviano.services.impl;

import ar.utn.ba.ddsi.cliente_liviano.models.dashboard.DashboardStats;
import ar.utn.ba.ddsi.cliente_liviano.models.ResultadoEstadisticaDTO;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DashboardBuilderService {

    private static final Pattern CAT_IN_HOUR =
            Pattern.compile("hora del d[ií]a.*categor[ií]a\\s+([^:.,;]+)\\s*.*", Pattern.CASE_INSENSITIVE);

    private static final Pattern CAT_IN_PROV =
            Pattern.compile("provincia con m[aá]s hechos.*categor[ií]a\\s+([^:.,;]+)\\s*.*", Pattern.CASE_INSENSITIVE);
    private static String fold(String s){
        if (s == null) return null;
        String n = Normalizer.normalize(s, Normalizer.Form.NFD);
        return n.replaceAll("\\p{M}","").toLowerCase(Locale.ROOT).trim();
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
                .map(ResultadoEstadisticaDTO::getFechaGeneracion)
                .filter(Objects::nonNull)
                .max(LocalDateTime::compareTo)
                .orElse(null));

        // TIPAR el comparador para no perder los genéricos
        Comparator<ResultadoEstadisticaDTO> byValorLuegoFecha = Comparator
                .comparingLong((ResultadoEstadisticaDTO d) ->
                        Optional.ofNullable(d.getValor()).orElse(0L)
                )
                .thenComparing(
                        ResultadoEstadisticaDTO::getFechaGeneracion,
                        Comparator.nullsLast(LocalDateTime::compareTo)
                );

        Map<String, ResultadoEstadisticaDTO> bestHourByCat = new LinkedHashMap<>();
        Map<String, ResultadoEstadisticaDTO> bestProvByCat = new LinkedHashMap<>();

        for (ResultadoEstadisticaDTO r : datos){
            String tituloFold = fold(r.getNombreEstadistica());
            if (tituloFold == null) continue;

            // 1) Categoria con mas hechos (global)
            if (tituloFold.startsWith("categoria con mas hechos")){
                vm.setCategoriaConMasHechos(
                        new DashboardStats.CategoryTop(r.getClave(), Optional.ofNullable(r.getValor()).orElse(0L)));
                continue;
            }

            // 2) Provincia con más hechos (global)
            if (tituloFold.equals("provincia con mas hechos")){
                vm.setProvinciaConMasHechos(
                        new DashboardStats.ProvinceTop(r.getClave(), Optional.ofNullable(r.getValor()).orElse(0L)));
                continue;
            }

            // 3) Hora top por categoría
            if (tituloFold.startsWith("hora del dia")){
                String cat = Optional.ofNullable(extractCategoria(r.getNombreEstadistica()))
                        .orElse(fold(r.getNombreDeLaColeccion()));
                if (cat != null){
                    bestHourByCat.merge(cat, r, (a,b) -> byValorLuegoFecha.compare(a,b) >= 0 ? a : b);
                }
                continue;
            }

            // 4) Provincia top por categoría
            if (tituloFold.startsWith("provincia con mas hechos de la categoria")
                    || tituloFold.startsWith("provincia con mas hechos en categoria")){
                String cat = Optional.ofNullable(extractCategoria(r.getNombreEstadistica()))
                        .orElse(fold(r.getNombreDeLaColeccion()));
                if (cat != null){
                    bestProvByCat.merge(cat, r, (a,b) -> byValorLuegoFecha.compare(a,b) >= 0 ? a : b);
                }
            }
        }

        // Mapea a objetos “bonitos”
        bestHourByCat.forEach((catFold, r) -> {
            String catOriginal = (r.getNombreDeLaColeccion()!=null)
                    ? r.getNombreDeLaColeccion()
                    : Optional.ofNullable(extractCategoria(r.getNombreEstadistica())).orElse(catFold); // <- cambio clave
            vm.getHoraTopPorCategoria().add(
                    new DashboardStats.HourTopByCategory(
                            catOriginal,
                            r.getClave(), // acá sí: la HORA está en clave
                            Optional.ofNullable(r.getValor()).orElse(0L)
                    )
            );
        });

        bestProvByCat.forEach((catFold, r) -> {
            String catKey = (r.getNombreDeLaColeccion()!=null)
                    ? r.getNombreDeLaColeccion()
                    : Optional.ofNullable(extractCategoria(r.getNombreEstadistica())).orElse(catFold); // <- cambio clave
            vm.getProvinciaTopPorCategoria().put(
                    catKey,
                    new DashboardStats.ProvinceTop(
                            r.getClave(), // acá sí: la PROVINCIA está en clave
                            Optional.ofNullable(r.getValor()).orElse(0L)
                    )
            );
        });

        return vm;
    }
}
