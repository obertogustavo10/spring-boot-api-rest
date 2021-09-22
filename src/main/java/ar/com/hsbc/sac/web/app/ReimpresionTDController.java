package ar.com.hsbc.sac.web.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.hsbc.sac.web.model.DebitCardDTO;
import ar.com.hsbc.sac.web.model.TipoEmbozo;

@RestController
@RequestMapping("/reimpresion")
class ReimpresionTDController {

    @GetMapping("/tarjetas/detalle")
    public ResponseEntity<Transaccional> consultaDetalleTarjeta(@RequestParam("operationId") String operationId,
            @RequestParam("numero") String numero) {
        if ("4517610097274041".equals(numero.trim())) {
            return new ResponseEntity<>(Transaccional.builder().message("Descripción de embozo")
                    .detalleTarjeta(getDebitCardDetails(numero)).header(UtilsController.getSuccessResponse()).build(),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    Transaccional.builder().message("").detalleTarjeta(DebitCardDTO.builder().build())
                            .header(UtilsController.getNotFoundResponse()).build(),
                    HttpStatus.OK);
        }
    }

    @GetMapping("/tarjetas/embozos")
    public ResponseEntity<Transaccional> consultaEmbozoTarjeta(@RequestParam("operationId") String operationId,
            @RequestParam("codigo") String codigo) {
        if ("RE_TAR_PRE".equals(codigo.trim())) {
            return new ResponseEntity<>(
                    Transaccional.builder().embozos(getEmbozos()).header(UtilsController.getSuccessResponse()).build(),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    Transaccional.builder().embozos(List.of()).header(UtilsController.getNotFoundResponse()).build(),
                    HttpStatus.OK);
        }
    }

    private DebitCardDTO getDebitCardDetails(String numero) {
        return DebitCardDTO.builder().banelcoCardNumber(numero).cardType("Banelco").company("2").docType("DNI")
                .docType("000000010266305").embozo("114").habilitacion("AC_TAR_PRE").reprint("RE_TAR_PRE").build();
    }

    private List<TipoEmbozo> getEmbozos() {
        List<TipoEmbozo> embozos = new ArrayList<>();
        Random random = new Random();
        int codigo = random.ints(1, 10).findFirst().getAsInt();
        if (codigo > 5) {
            TipoEmbozo t1 = TipoEmbozo.builder().destino("Exterior").embozo("122").categoria("7").build();
            embozos.add(t1);
        }
        TipoEmbozo t2 = TipoEmbozo.builder().destino("Domicilio").embozo("114").categoria("7").build();
        embozos.add(t2);
        codigo = random.ints(1, 5).findFirst().getAsInt();
        if (codigo != 3) {
            TipoEmbozo t3 = TipoEmbozo.builder().destino("Sucursal").embozo("112").categoria("0").build();
            embozos.add(t3);
        }

        return embozos;
    }

}