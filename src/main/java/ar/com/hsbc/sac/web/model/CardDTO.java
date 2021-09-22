package ar.com.hsbc.sac.web.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardDTO {

    private String numero, tipo, codigoAdmin, bancoOrigen, tipoDocumento, nombres, estado, numeroDocumento,
            codigoEstado, categoria, ajuste;

}
