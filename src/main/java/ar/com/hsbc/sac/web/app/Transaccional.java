package ar.com.hsbc.sac.web.app;

import java.util.List;

import ar.com.hsbc.sac.web.model.Authority;
import ar.com.hsbc.sac.web.model.BaseResponse;
import ar.com.hsbc.sac.web.model.CardAccessArrangement;
import ar.com.hsbc.sac.web.model.ClienteExtendidoDTO;
import ar.com.hsbc.sac.web.model.DebitoAutomaticoMDW;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
class Transaccional {

    private BaseResponse header;
    private String message;
    private ClienteExtendidoDTO client;
    private DebitoAutomaticoMDW debitosCta;
    private List<Authority> authorities;
    private List<Registration> registrations;
    private List <CardAccessArrangement> cardAccArr;

}