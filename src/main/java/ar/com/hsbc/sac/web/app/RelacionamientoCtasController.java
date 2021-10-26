package ar.com.hsbc.sac.web.app;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.hsbc.sac.web.model.CuentaRelDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("relacionamiento/cuentas")
public class RelacionamientoCtasController {

    private CuentaRelDTO [] cuentaRel=new CuentaRelDTO[4];

    private Map<String, CuentaRelDTO[]> cuentaRelMap = new HashMap<>();

    private void setAccountsRel(){
        cuentaRel[0] = CuentaRelDTO.builder().account("3003339788").accountRelated("").accounts(null).banelcoCard("")
            .codigo(null).detProduct("Cuenta Corriente $").document("000000010266305").indivEmpres("0").ordConj("2")
            .product("3003339788").resultState(false).status("").tipDoc("DNI").tipProduct("0").tipRelatedAccount("").denominacion("").build();
        
        cuentaRel[1] = CuentaRelDTO.builder().account("0016068441").accountRelated("").accounts(null).banelcoCard("")
            .codigo(null).detProduct("Caja Ahorros $").document("000000010266305").indivEmpres("0").ordConj("2")
            .product("0016068441").resultState(false).status("Cuenta Principal").tipDoc("DNI").tipProduct("2").tipRelatedAccount("").denominacion("").build();
        
        cuentaRel[2] = CuentaRelDTO.builder().account("3006507476").accountRelated("").accounts(null).banelcoCard("")
            .codigo(null).detProduct("Caja Ahorros $").document("000000010266305").indivEmpres("0").ordConj("2")
            .product("3006507476").resultState(false).status("").tipDoc("DNI").tipProduct("2").tipRelatedAccount("").denominacion("").build();
        
        cuentaRel[3] = CuentaRelDTO.builder().account("3008265831").accountRelated("").accounts(null).banelcoCard("")
            .codigo(null).detProduct("Caja Ahorros u$s").document("000000010266305").indivEmpres("0").ordConj("2")
            .product("3008265831").resultState(false).status("Cuenta Primaria").tipDoc("DNI").tipProduct("2").tipRelatedAccount("").denominacion("").build();
        cuentaRelMap.put("DNI102663054517610097274041", cuentaRel);
    }

    @GetMapping(value="consulta")
    public ResponseEntity<Transaccional> consultaCuentasRelacionadas(@RequestParam("operationId") String operationId, @RequestParam("tipDoc") String tipDoc,
                         @RequestParam("documento") String documento, @RequestParam("producto") String producto) {
        this.setAccountsRel();
        Transaccional transacc=null;
        if(cuentaRelMap.containsKey(tipDoc+documento+producto)){
            for(CuentaRelDTO ctaRel:cuentaRelMap.get(tipDoc+documento+producto)){
                if(!"Cuenta Principal".equals(ctaRel.getStatus().trim()) && !"Cuenta Primaria".equals(ctaRel.getStatus().trim())){
                        ctaRel.setStatus("Sin Asignar");
                }
                ctaRel.setDenominacion(ctaRel.getStatus());
            }
            transacc = Transaccional.builder().relatedAccounts(Arrays.asList(cuentaRelMap.get(tipDoc+documento+producto)))
                .header(UtilsController.getSuccessResponse()).build();

        }else {
            transacc = Transaccional.builder().header(UtilsController.getNotFoundResponse()).build();
        }
    return new ResponseEntity<>(transacc, HttpStatus.OK);
    }
    
    
}
