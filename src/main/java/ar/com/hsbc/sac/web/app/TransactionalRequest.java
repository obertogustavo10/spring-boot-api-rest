package ar.com.hsbc.sac.web.app;

import ar.com.hsbc.sac.web.model.ClienteExtendidoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionalRequest {

    private TransaccionalDTO commonParams;
    private ClienteExtendidoDTO client;
    private ReimpresionTarjetaDTO reprintTdParams;
    private CambioCierreDTO cambioCierreParams;

}
