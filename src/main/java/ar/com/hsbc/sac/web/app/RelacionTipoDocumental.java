package ar.com.hsbc.sac.web.app;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RelacionTipoDocumental {

    private String causa;
    private String motivo;
    private String tipoDocumental;
    private String clasificacion;
    private String temporal;
    private String estado;
    private String accion;
    private String usuario;
    private String pc;
    private String fecha;
    private String codCausa;
    private String codMotivo;
    private String codTipoDocumental;
    private String codClasificacion;
    private String codAcc;
    private boolean existeEnSAC;
    private boolean esAddFiles;

}
