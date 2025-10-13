package ar.utn.ba.ddsi.cliente_liviano.models.dashboard;

import java.time.LocalDateTime;
import java.util.*;

public class DashboardStats {

    public static class CategoryTop {
        private String categoria;
        private long cantidad;
        public CategoryTop() {}
        public CategoryTop(String categoria, long cantidad){ this.categoria=categoria; this.cantidad=cantidad; }
        public String getCategoria(){ return categoria; }
        public void setCategoria(String categoria){ this.categoria=categoria; }
        public long getCantidad(){ return cantidad; }
        public void setCantidad(long cantidad){ this.cantidad=cantidad; }
    }

    public static class ProvinceTop {
        private String provincia;
        private long cantidad;
        public ProvinceTop() {}
        public ProvinceTop(String provincia, long cantidad){ this.provincia=provincia; this.cantidad=cantidad; }
        public String getProvincia(){ return provincia; }
        public void setProvincia(String provincia){ this.provincia=provincia; }
        public long getCantidad(){ return cantidad; }
        public void setCantidad(long cantidad){ this.cantidad=cantidad; }
    }

    public static class HourTopByCategory {
        private String categoria;
        private String hora;
        private long cantidad;
        public HourTopByCategory() {}
        public HourTopByCategory(String categoria, String hora, long cantidad){
            this.categoria=categoria; this.hora=hora; this.cantidad=cantidad;
        }
        public String getCategoria(){ return categoria; }
        public void setCategoria(String categoria){ this.categoria=categoria; }
        public String getHora(){ return hora; }
        public void setHora(String hora){ this.hora=hora; }
        public long getCantidad(){ return cantidad; }
        public void setCantidad(long cantidad){ this.cantidad=cantidad; }
    }

    private LocalDateTime generado;
    private CategoryTop categoriaConMasHechos;
    private ProvinceTop provinciaConMasHechos;
    private Map<String, ProvinceTop> provinciaTopPorCategoria = new LinkedHashMap<>();
    private List<HourTopByCategory> horaTopPorCategoria = new ArrayList<>();
    private Map<String, ProvinceTop> provinciaTopPorColeccion = new LinkedHashMap<>();

    public LocalDateTime getGenerado(){ return generado; }
    public void setGenerado(LocalDateTime generado){ this.generado=generado; }
    public CategoryTop getCategoriaConMasHechos(){ return categoriaConMasHechos; }
    public void setCategoriaConMasHechos(CategoryTop c){ this.categoriaConMasHechos=c; }
    public ProvinceTop getProvinciaConMasHechos(){ return provinciaConMasHechos; }
    public void setProvinciaConMasHechos(ProvinceTop p){ this.provinciaConMasHechos=p; }
    public Map<String, ProvinceTop> getProvinciaTopPorCategoria(){ return provinciaTopPorCategoria; }
    public List<HourTopByCategory> getHoraTopPorCategoria(){ return horaTopPorCategoria; }
    public Map<String, ProvinceTop> getProvinciaTopPorColeccion() { return provinciaTopPorColeccion; }
}
