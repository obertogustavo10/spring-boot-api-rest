package ar.com.hsbc.sac.web.app;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.hsbc.sac.web.model.CardDTO;

@RestController
@RequestMapping("/cambioCierre")
public class CambioCierreController {
        private CardDTO[] cards = new CardDTO[2];
        private Map<String, CardDTO[]> enquireCard = new HashMap<>();

        private void setCards() {
                cards[0] = CardDTO.builder().numero("3778040002377760").nombres("STEFANOLO ESTELA N")
                                .tipoDocumento("DNI").numeroDocumento("10266305").bancoOrigen("HSBC BANK ARGENTINA")
                                .tipo("Titular").codigoAdmin("7").codigoEstado("4").categoria("4").estado("OPERATIVA")
                                .build();
                cards[1] = CardDTO.builder().numero("5360873721102328").nombres("STEFANOLO ESTELA N")
                                .tipoDocumento("DNI").numeroDocumento("10266305").bancoOrigen("HSBC BANK ARGENTINA")
                                .tipo("Titular").codigoAdmin("7").codigoEstado("4").categoria("4").estado("OPERATIVA")
                                .build();
                enquireCard.put("DNI10266305", cards);
        }

        @GetMapping("/tarjetas")
        public ResponseEntity<Transaccional> consultaTarjeta(@RequestParam("operationId") String operationId,
                        @RequestParam("docType") String docType, @RequestParam("docNum") String docNum) {
                this.setCards();
                String documento = docType + docNum;
                Transaccional transacc = null;
                if (enquireCard.containsKey(documento)) {
                        transacc = Transaccional.builder().cards(Arrays.asList(enquireCard.get(documento)))
                                        .header(UtilsController.getSuccessResponse()).build();
                } else {
                        transacc = Transaccional.builder().header(UtilsController.getNotFoundResponse()).build();
                }
                return new ResponseEntity<>(transacc, HttpStatus.OK);
        }

}
