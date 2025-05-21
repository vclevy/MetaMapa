package ar.utn.ba.ddsi.FuenteProxy.models.dtos.external;

import ar.utn.ba.ddsi.FuenteProxy.models.dtos.HechoProxyDTO;

import java.util.List;

public class HechoRespuesta {
    private List<HechoProxyDTO> data;

    public List<HechoProxyDTO> getData() {
        return data;
    }

    public void setData(List<HechoProxyDTO> data) {
        this.data = data;
    }
}
