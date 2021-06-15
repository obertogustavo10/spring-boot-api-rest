package ar.com.hsbc.sac.web.app;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.com.hsbc.sac.web.model.AccountAccessCard;
import ar.com.hsbc.sac.web.model.AccountArrangement;
import ar.com.hsbc.sac.web.model.CardAccessArrangement;
import ar.com.hsbc.sac.web.model.GSMAmount;
import ar.com.hsbc.sac.web.model.InsertUnsubscribeAdditionalDTO;
import ar.com.hsbc.sac.web.model.InsertUnsubscribeAddtionalDTO;
import ar.com.hsbc.sac.web.model.RelateFileDTO;

@RestController
@RequestMapping("/bajaAdicionalTC")
public class BajaAdicionalTCController {

    private CardAccessArrangement [] cardAccessArrangements = new CardAccessArrangement[3];
    private CardAccessArrangement cardAccArrgmts;
    private Map<String, CardAccessArrangement[]> enquireCard = new HashMap<>();
    private Map<String, CardAccessArrangement> buscSucLimCred= new HashMap<>();
    private void downCard(){
        cardAccessArrangements[0]=CardAccessArrangement.builder().cardNum("3778040002377760").cardhName("STEFANOLO ESTELA N")
        .typeIdfcCde("DNI").idfcNum("10266305").primAcctInfo(AccountArrangement.builder().acctShrtSLName("HSBC BANK ARGETINA")
        .soleJointCde("0").restrCde("7").statCde("4").restrDesc("AMERICAN EXPRESS").statDesc("OPERATIVA").build())
        .build();

        cardAccessArrangements[1]=CardAccessArrangement.builder().cardNum("3778040002406300").cardhName("VERA LUIS MARIO SANTIAGO")
        .typeIdfcCde("DNI").idfcNum("10049456").acctAccessCard(AccountAccessCard.builder().issCde("4").ofstPINNum("70010056470494")
        .userSLName("0").build()).primAcctInfo(AccountArrangement.builder().soleJointCde("1").restrCde("7")
        .statCde("8").restrDesc("AMERICAN EXPRESS").statDesc("BAJA").build()).build();

        cardAccessArrangements[2]=CardAccessArrangement.builder().cardNum("53608727560669766").cardhName("IPARRAGUIRRE GUILLERMO")
        .cycLmtCashWdrwAmt(GSMAmount.builder().amt(BigDecimal.valueOf(0.0)).build()).cycLmtTrsWithnAmt(GSMAmount.builder()
        .amt(BigDecimal.valueOf(0.0)).build()).cycLmtPurchAmt(GSMAmount.builder().amt(BigDecimal.valueOf(0.0)).build()).typeIdfcCde("DNI")
        .idfcNum("10129290").acctAccessCard(AccountAccessCard.builder().issCde("4").ofstPINNum("1100200113387")
        .userSLName("13338729").build()).primAcctInfo(AccountArrangement.builder().soleJointCde("2").restrCde("1")
        .statCde("4").restrDesc("MASTERCARD").statDesc("OPERATIVA").build()).build();

        enquireCard.put("DNI10266305", cardAccessArrangements);

        cardAccArrgmts=CardAccessArrangement.builder()
            .cycCashWdrwVIPAmt(GSMAmount.builder().amt(BigDecimal.valueOf(226000.0)).build())
            .cycLmtCashWdrwAmt(GSMAmount.builder().amt(BigDecimal.valueOf(0.0)).build())
            .cycLmtTrsWithnAmt(GSMAmount.builder().amt(BigDecimal.valueOf(0.0)).build())
            .cycLmtPurchAmt(GSMAmount.builder().amt(BigDecimal.valueOf(0.0)).build())
            .primAcctInfo(AccountArrangement.builder().statCde("30").statDesc("PERSONAL HSBC").build())
            .build();
        buscSucLimCred.put("11113338704", cardAccArrgmts);
    }

    @GetMapping("/bajaTc")
    public ResponseEntity<Transaccional> bajaTC(@RequestParam("operationId") String operationId,
    @RequestParam("docType") String docType, @RequestParam("docNum") String docNum) {
        this.downCard();
        Transaccional transaccional=null;
        String documento = docType + docNum;
        if (enquireCard.containsKey(documento)) {

            transaccional = Transaccional.builder().cardAccArr(Arrays.asList(enquireCard.get(documento)))
                    .header(UtilsController.getSuccessResponse()).build();

        } else {
            transaccional = Transaccional.builder().header(UtilsController.getNotFoundResponse()).build();
        }
        return new ResponseEntity<>(transaccional,HttpStatus.OK);

    } 
    
    @GetMapping("/buscaSucursalLimCred")
    public ResponseEntity<Transaccional> buscaSucursalLimCred(@RequestParam("operationId") String operationId,
        @RequestParam("admEntityCde") String admEntityCde, 
        @RequestParam("entityCde") String entityCde,
        @RequestParam ("creditCardNumber") String creditCardNumber){
            this.downCard();
            Transaccional transaccional = null;
            if(buscSucLimCred.containsKey(admEntityCde+entityCde+creditCardNumber)){
                transaccional = Transaccional.builder().cardAccArr(Arrays.asList(buscSucLimCred.get(admEntityCde+entityCde+creditCardNumber)))
                .header(UtilsController.getSuccessResponse()).build();
            }else{
                transaccional = Transaccional.builder().header(UtilsController.getNotFoundResponse()).build();
            }
            return new ResponseEntity<>(transaccional, HttpStatus.OK);
        }
        @PostMapping("/insertUnsubscribeAdditional")
        public ResponseEntity<Transaccional> insertUnsubscribeAdditional(@RequestBody InsertUnsubscribeAddtionalDTO insertUnsubscribeAddtionalDTO) {
                    Transaccional transaccional = Transaccional.builder().header(UtilsController.getSuccessResponse())
                        .build();
                System.out.println("insert: "+insertUnsubscribeAddtionalDTO);
            return new ResponseEntity<>(transaccional, HttpStatus.OK);

        }
        @PostMapping("/relateFiles")
        public ResponseEntity<Transaccional> relateFiles(@RequestBody RelateFileDTO relateFileDTO) {
                    Transaccional transaccional = Transaccional.builder().header(UtilsController.getSuccessResponse()).status("true")
                        .build();
        
                    System.out.println("relateFile: "+ relateFileDTO);
                        return new ResponseEntity<>(transaccional, HttpStatus.OK);
        
                        }

        
            
        

}
