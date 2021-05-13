package ar.com.hsbc.sac.web.app;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.hsbc.sac.web.model.AccountAccessCard;
import ar.com.hsbc.sac.web.model.AccountArrangement;
import ar.com.hsbc.sac.web.model.CardAccessArrangement;
import ar.com.hsbc.sac.web.model.GSMAmount;

@RestController
@RequestMapping("/cambioCierre")
public class CambioCierreController {
    private static CardAccessArrangement [] cardAccessArrangements= new CardAccessArrangement[3];
    private static Map <String,CardAccessArrangement []> enquireCard= new HashMap<>();
    static{
            cardAccessArrangements[0]=CardAccessArrangement.builder().cardNum("3778040002377760")
            .cardhName("STEFANOLO ESTELA N").typeIdfcCde("DNI").idfcNum("10266305")
            .primAcctInfo(AccountArrangement.builder().acctShrtSLName("HSBC BANK ARGENTINA")
            .soleJointCde("0").restrCde("7").statCde("4").restrDesc("AMERICAN EXPRESS").statDesc("OPERATIVA").build())
            .build();
            cardAccessArrangements[1]=CardAccessArrangement.builder().cardNum("5360873721102328")
            .cardhName("STEFANOLO ESTELA N").typeIdfcCde("DNI").idfcNum("10266305")
            .primAcctInfo(AccountArrangement.builder().acctShrtSLName("HSBC BANK ARGENTINA")
            .soleJointCde("0").restrCde("1").statCde("4").restrDesc("MASTERCARD").statDesc("OPERATIVA").build())
            .build();
            cardAccessArrangements[2]=CardAccessArrangement.builder().cardNum("53608727560669766")
            .cardhName("IPARRAGUIRRE GUILLERMO").cycLmtCashWdrwAmt(GSMAmount.builder().amt(BigDecimal.valueOf(0.0))
            .build()).cycLmtTrsWithnAmt(GSMAmount.builder().amt(BigDecimal.valueOf(0.0))
            .build()).cycLmtPurchAmt(GSMAmount.builder().amt(BigDecimal.valueOf(0.0))
            .build()).typeIdfcCde("DNI").idfcNum("10129290").acctAccessCard(AccountAccessCard.builder()
            .issCde("4").ofstPINNum("11002001133387").userSLName("113338729").build())
            .primAcctInfo(AccountArrangement.builder().acctShrtSLName("")
            .soleJointCde("2").restrCde("1").statCde("4").restrDesc("MASTERCARD").statDesc("OPERATIVA").build())
            .build();
        
        enquireCard.put("DNI10266305", cardAccessArrangements);
    }
    
    @GetMapping("/tarjetas")
    public ResponseEntity<Transaccional> consultaTarjeta(
       @RequestParam("operationId") String operationId,
        @RequestParam("docType") String docType, 
        @RequestParam("docNum") String docNum){
            String documento = docType+docNum;
            Transaccional transacc=null;
            if(enquireCard.containsKey(documento)){
                
                transacc=Transaccional.builder().cardAccArr(Arrays.asList(enquireCard.get(documento))).header(UtilsController.getSuccessResponse()).build();
                
            }else{
                transacc=Transaccional.builder().header(UtilsController.getNotFoundResponse()).build();
            }
            return new ResponseEntity<>(transacc, HttpStatus.OK);
    } 
    
}
