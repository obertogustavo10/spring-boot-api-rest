package ar.com.hsbc.sac.web.app;

import java.util.List;

import ar.com.hsbc.sac.web.model.Authority;
import ar.com.hsbc.sac.web.model.BaseResponse;
import ar.com.hsbc.sac.web.model.Branch;
import ar.com.hsbc.sac.web.model.ClienteExtendidoDTO;
import ar.com.hsbc.sac.web.model.DebitCardDTO;
import ar.com.hsbc.sac.web.model.ResponseMDW;
import ar.com.hsbc.sac.web.model.TipoEmbozo;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
class Transaccional {

    private BaseResponse header;
    private String message;
    private Registration registration;
    private ClienteExtendidoDTO client;
    private ResponseMDW debitosCta;
    private List<Authority> authorities;
    private DebitCardDTO detalleTarjeta;
    private List<TipoEmbozo> embozos;
    private List<Branch> branches;
    private ResponseMDW permisosMaxima;

}