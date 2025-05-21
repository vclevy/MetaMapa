package ar.utn.ba.ddsi.FuenteProxy.models.dtos.external;

import ar.utn.ba.ddsi.FuenteProxy.models.dtos.ColeccionDTO;

import java.util.List;

public class ColeccionRespuesta {
    private List<ColeccionDTO> data;

    public List<ColeccionDTO> getData() {
        return data;
    }

    public void setData(List<ColeccionDTO> data) {
        this.data = data;
    }
}
