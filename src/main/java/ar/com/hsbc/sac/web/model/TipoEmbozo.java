package ar.com.hsbc.sac.web.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TipoEmbozo {

    private String destino;
    private String embozo;
    private String categoria;

}
