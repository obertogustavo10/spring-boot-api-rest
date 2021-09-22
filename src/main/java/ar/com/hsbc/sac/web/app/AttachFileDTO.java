package ar.com.hsbc.sac.web.app;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttachFileDTO {

    private String arcId;
    private String arcNumPed;
    private String arcTipDoc;
    private String arcNumDoc;
    private String arcNomUsu;
    private String arcNombreOri;
    private String arcNombreDef;
    private String arcFecha;
    private String arcClasificacion;
    private String arcTipodocumental;
    private String arcEstado;
    private String arcRepoDef;
    private String arcRepoId;
    private String arcRepoIdActual;
    private String arcRepoVersion;
    private String arcPath;
    private String arcFileContent;
    private String arcCodTipodocumental;
    private String arcTamano;
    private boolean arcAttached;
    private boolean arcIsNew;
    private MultipartFile file;

}
