package ar.com.hsbc.sac.web.app;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CambioCierreDTO {
    private String tipoCliente, apellidoNombre, nroTarjeta, estado;

}
