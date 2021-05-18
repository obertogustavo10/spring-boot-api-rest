package ar.com.hsbc.sac.web.app;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.hsbc.sac.web.model.BaseResponse;
import ar.com.hsbc.sac.web.model.DebitCardDTO;
import ar.com.hsbc.sac.web.model.ErrorResponse;
import ar.com.hsbc.sac.web.model.TipoEmbozo;

@RestController
@RequestMapping("/reimpresion")
class ReimpresionTDController {

    @GetMapping("/tarjetas/detalle")
    public ResponseEntity<Transaccional> consultaDetalleTarjeta(@RequestParam("operationId") String operationId,
            @RequestParam("numero") String numero) {
        if ("4517610097274041".equals(numero.trim())) {
            return new ResponseEntity<>(Transaccional.builder().detalleTarjeta(getDebitCardDetails(numero))
                    .header(getSuccessResponse()).build(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Transaccional.builder().detalleTarjeta(DebitCardDTO.builder().build())
                    .header(getNotFoundResponse()).build(), HttpStatus.OK);
        }
    }

    @GetMapping("/tarjetas/embozos")
    public ResponseEntity<Transaccional> consultaEmbozoTarjeta(@RequestParam("operationId") String operationId,
            @RequestParam("codigo") String codigo) {
        if ("RE_TAR_PRE".equals(codigo.trim())) {
            return new ResponseEntity<>(
                    Transaccional.builder().embozos(getEmbozos()).header(getSuccessResponse()).build(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    Transaccional.builder().embozos(List.of()).header(getNotFoundResponse()).build(), HttpStatus.OK);
        }
    }

    private DebitCardDTO getDebitCardDetails(String numero) {
        return DebitCardDTO.builder().banelcoCardNumber(numero).cardType("Banelco").company("2").docType("DNI")
                .docType("000000010266305").embozo("114").habilitacion("AC_TAR_PRE").reprint("RE_TAR_PRE").build();
    }

    private List<TipoEmbozo> getEmbozos() {
        TipoEmbozo t1 = TipoEmbozo.builder().destino("Exterior").embozo("122").categoria("7").build();
        TipoEmbozo t2 = TipoEmbozo.builder().destino("Domicilio").embozo("114").categoria("7").build();
        TipoEmbozo t3 = TipoEmbozo.builder().destino("Sucursal").embozo("112").categoria("0").build();
        return List.of(t1, t2, t3);
    }

    // UTILS
    private BaseResponse getSuccessResponse() {
        return BaseResponse.builder().apiVersion("1.0").operationId("1509090912980128785211").statusCode("200")
                .statusMessage("OK").successResponse("Ejecutado exitosamente!").build();
    }

    private BaseResponse getNotFoundResponse() {
        ErrorResponse errorResponse = ErrorResponse.builder().faultString("SQLServerException")
                .detail("The resultset returned an empty body.").faultCode("ESSR").build();
        return BaseResponse.builder().apiVersion("1.0").operationId("1509090912980128785212").statusCode("404")
                .statusMessage("NOTFOUND").errorResponse(errorResponse).build();
    }

    private BaseResponse getInternalErrorResponse() {
        ErrorResponse errorResponse = ErrorResponse.builder().faultString("ServerException").detail("Internal Error.")
                .faultCode("ESSR").build();
        return BaseResponse.builder().apiVersion("1.0").operationId("1509090912980128785213").statusCode("500")
                .statusMessage("INTERNALERROR").errorResponse(errorResponse).build();
    }

}