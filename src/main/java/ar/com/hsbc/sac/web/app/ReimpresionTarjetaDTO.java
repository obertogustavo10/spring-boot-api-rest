package ar.com.hsbc.sac.web.app;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReimpresionTarjetaDTO {

    // Relativo a Reimpresion Comun / Diferida
    private String embozo;
    private String category;
    private String domicilio;
    private String sucursal;

}
