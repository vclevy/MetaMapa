package ar.utn.ba.ddsi.FuenteProxy.models.dtos;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PaginatedResponseDTO<T> {
    private int current_page;
    private List<T> data;
    private String first_page_url;
    private int last_page;
    private String last_page_url;
    private String next_page_url;
    private String prev_page_url;
    private int per_page;
    private int total;

}
