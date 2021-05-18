package ar.com.hsbc.sac.web.app;

import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.hsbc.sac.web.model.Authority;

import ar.com.hsbc.sac.web.model.Branch;
import ar.com.hsbc.sac.web.model.ClienteExtendidoDTO;
import ar.com.hsbc.sac.web.model.CreditCardAdhesion;
import ar.com.hsbc.sac.web.model.CuentaDTO;
import ar.com.hsbc.sac.web.model.ResponseMDW;

@RestController
@RequestMapping("/transaccional")
public class TransaccionalesController {

        private static Map<String, ClienteExtendidoDTO> clientes = new HashMap<>();
        private static Map<String, ResponseMDW> debitosCuentaMDW = new HashMap<>();
        private static Map<String, List<Authority>> authorities = new HashMap<>();

        @GetMapping("/clientes/lista")
        public ResponseEntity<List<ClienteExtendidoDTO>> consultaClienteTodos() {
                return new ResponseEntity<>(new ArrayList<>(clientes.values()), HttpStatus.OK);
        }

        @GetMapping("/clientes")
        public ResponseEntity<Transaccional> consultaCliente(@RequestParam("operationId") String operationId,
                        @RequestParam("documentType") String docType,
                        @RequestParam("documentNumber") String documentNumber) {
                Transaccional transaccional = null;
                ClienteExtendidoDTO client = clientes.get(docType + documentNumber);
                if (client != null) {
                        transaccional = Transaccional.builder().client(client)
                                        .header(UtilsController.getSuccessResponse()).build();
                } else {
                        transaccional = Transaccional.builder().header(UtilsController.getNotFoundResponse()).build();
                }
                dormir(500);
                return new ResponseEntity<>(transaccional, HttpStatus.OK);
        }

        @PostMapping("/clientes/alta")
        public ResponseEntity<ClienteExtendidoDTO> altaCliente(@RequestBody ClienteExtendidoDTO client) {
                clientes.put(client.getDocType() + client.getDocument(), client);
                return new ResponseEntity<>(client, HttpStatus.OK);
        }

        @GetMapping("/cuentas/debitos/consulta")
        public ResponseEntity<Transaccional> consultaDebitosCuenta(@RequestParam("operationId") String operationId,
                        @RequestParam("docType") String docType, @RequestParam("document") String document) {
                Transaccional transaccional = null;
                ResponseMDW debito = debitosCuentaMDW.get(docType + document);
                if (debito != null) {
                        transaccional = Transaccional.builder().debitosCta(debito)
                                        .header(UtilsController.getSuccessResponse()).build();
                } else {
                        transaccional = Transaccional
                                        .builder().debitosCta(ResponseMDW.builder().returnCode("02")
                                                        .returnDescription("NOTFOUND").build())
                                        .header(UtilsController.getSuccessResponse()).build();
                }
                dormir(500);
                return new ResponseEntity<>(transaccional, HttpStatus.OK);
        }

        @PostMapping("/cuentas/debitos/alta")
        public ResponseEntity<Transaccional> altaDebitoCta(@RequestParam("operationId") String operationId,
                        @RequestBody List<CuentaDTO> debitosCta) {
                debitosCta.stream().forEach(debito -> System.out.println("Alta de nuevo Debito CTA: " + debito));

                dormir(500);
                return new ResponseEntity<>(Transaccional.builder().registration(getRegistration())
                                .header(UtilsController.getSuccessResponse()).build(), HttpStatus.OK);
        }

        @PostMapping("/grabar")
        public ResponseEntity<Transaccional> grabarTransaccional(
                        @RequestBody TransactionalRequest transactionalRequest) {
                System.out.println("Grabar Transaccional " + transactionalRequest.getOption() + ": "
                                + transactionalRequest);
                dormir(500);
                return new ResponseEntity<>(Transaccional.builder().registration(getRegistration())

                                .header(UtilsController.getSuccessResponse()).build(), HttpStatus.OK);
        }

        @GetMapping("/entes/subentes")
        public ResponseEntity<Transaccional> consultarEntes(@RequestParam("operationId") String operationId,
                        @RequestParam("cuit") String cuit) {
                Transaccional transaccional = null;
                List<Authority> authorityList = authorities.get(cuit);
                if (authorityList != null && !authorityList.isEmpty()) {
                        transaccional = Transaccional.builder().authorities(authorityList)
                                        .header(UtilsController.getSuccessResponse()).build();
                } else {
                        transaccional = Transaccional.builder().header(UtilsController.getNotFoundResponse()).build();
                }
                dormir(500);
                return new ResponseEntity<>(transaccional, HttpStatus.OK);
        }

        @GetMapping("/parametros/ayuda")
        public ResponseEntity<Transaccional> consultarTenerEnCuenta(@RequestParam("operationId") String operationId,
                        @RequestParam("productCode") String productCode, @RequestParam("causeCode") String causeCode,
                        @RequestParam("reasonCode") String reasonCode,
                        @RequestParam("companyCode") String companyCode) {
                Transaccional transaccional = Transaccional.builder()
                                .message("COMPLETAR TODOS LOS DATOS SOLICITADOS EN CADA CASO.")
                                .header(UtilsController.getSuccessResponse()).build();
                dormir(500);
                return new ResponseEntity<>(transaccional, HttpStatus.OK);
        }

        @GetMapping("/sucursales")
        public ResponseEntity<Transaccional> consultaSucursales(@RequestParam("operationId") String operationId) {
                return new ResponseEntity<>(Transaccional.builder().branches(getSucursales())
                                .header(UtilsController.getSuccessResponse()).build(), HttpStatus.OK);
        }

        @GetMapping("/maxima/permiso")
        public ResponseEntity<Transaccional> consultaPermisoMaxima(@RequestParam("operationId") String operationId,
                        @RequestParam("transaccional") String transaccional) {
                if (validarTransaccional(transaccional)) {
                        return new ResponseEntity<>(Transaccional.builder()
                                        .permisosMaxima(ResponseMDW.builder().returnCode("0000").returnType("OK")
                                                        .returnDescription("Credenciales encontradas").activityFlag("1")
                                                        .build())
                                        .header(UtilsController.getSuccessResponse()).build(), HttpStatus.OK);
                } else {
                        return new ResponseEntity<>(Transaccional.builder()
                                        .permisosMaxima(ResponseMDW.builder().returnCode("0000").returnType("OK")
                                                        .returnDescription("Credenciales encontradas").activityFlag("0")
                                                        .build())
                                        .header(UtilsController.getSuccessResponse()).build(), HttpStatus.OK);
                }

        }

        private List<Branch> getSucursales() {
                List<Branch> branches = new ArrayList<>();
                for (int i = 0; i < 50; i++) {
                        Random random = new Random();
                        int codigo = random.ints(1, 500).findFirst().getAsInt();
                        branches.add(Branch.builder().numBranch(String.valueOf(codigo)).branch("Sucursal " + codigo)
                                        .build());
                }
                Comparator<Branch> comparator = (branch1, branch2) -> Integer.valueOf(branch1.getNumBranch())
                                - Integer.valueOf(branch2.getNumBranch());
                branches.sort(comparator);
                return branches;
        }

        static {
                // DNI 10266305
                clientes.put("DNI10266305", ClienteExtendidoDTO.builder().businessName("BRINGIOTTI, MANUEL")
                                .docType("DNI").document("10266305").age("59").bank("HSBC BANK ARG SA")
                                .birthDay(new Date()).category("P").categoryCis("PLB").cpPart("000000")
                                .cpCom("CAPITAL FEDERAL 01 1005").filingOffice("300")
                                .localityCom("CAPITAL FEDERAL 01 1005").localityPart("CIUDAD AUTONOMA BUEN")
                                .nationality("080").mail("juanperez@gmail.com").phoneCom("011 45645464")
                                .phonePart("011 45645464").residenceCom("FLORIDA 229 10")
                                .residencePart("AVDA NAZCA 3103 3 C").build());

                debitosCuentaMDW.put("DNI10266305", ResponseMDW.builder().returnCode("00").returnDescription("00000000")
                                .clientNumber("00000000097274").creditCardAdhesions(getCreditCardAdhesions()).build());

                // DNI 34129654
                clientes.put("DNI34129654", ClienteExtendidoDTO.builder().businessName("EXPOSITO, LAMAS JOSE")
                                .docType("DNI").document("34129654").age("89").bank("HSBC BANK ARG")
                                .birthDay(new Date()).category("P").categoryCis("PLB").cpPart("000000")
                                .cpCom("CAPITAL FEDERAl").filingOffice("300").localityCom("CAPITAL FEDERAL AR")
                                .localityPart("CABA").nationality("080").mail("expositolamas@gmail.com")
                                .phoneCom("011-43245464").phonePart("011-43245464").residenceCom("LARREA 1254 8 E")
                                .residencePart("AVDA LIBERTADOR 4200 2 A").build());

                debitosCuentaMDW.put("DNI34129654", ResponseMDW.builder().returnCode("00").returnDescription("00000000")
                                .clientNumber("00000000067420").build());

                // Authorities
                // 20110083298
                Authority auth1 = Authority.builder().admitsReservation("1").authority("88887")
                                .cuitNumber("20110083298").description("HUGO GUTIERREZ   ").estc("0")
                                .longReferenceN("7").originBank("00070999").originBankName("BANCO DE GALICIA")
                                .presentation("ALARMAS").registerType("5").subAuthority("195").build();
                Authority auth2 = Authority.builder().admitsReservation("1").authority("88887")
                                .cuitNumber("20110083298").description("AYS ALARMAS     ").estc("0").longReferenceN("6")
                                .originBank("00270055").originBankName("BANCO SUPERVIELLE  ").presentation("ABONO")
                                .registerType("5").subAuthority("600").build();
                authorities.put("20110083298", List.of(auth1, auth2));

                // 20110083298
                auth1 = Authority.builder().admitsReservation("1").authority("88888").cuitNumber("20108496380")
                                .description("JUAN GARCIA   ").estc("0").longReferenceN("9").originBank("00070999")
                                .originBankName("BANCO DE GALICIA Y BUENOS AIRES").presentation("HOGAR")
                                .registerType("5").subAuthority("200").build();
                auth2 = Authority.builder().admitsReservation("1").authority("88888").cuitNumber("20108496380")
                                .description("TOTALHOGAR").estc("0").longReferenceN("9").originBank("00270055")
                                .originBankName("BANCO SUPERVIELLE  ").presentation("CUOTA").registerType("5")
                                .subAuthority("250").build();
                var auth3 = Authority.builder().admitsReservation("1").authority("88888").cuitNumber("20108496380")
                                .description("COMPRAS PROGRAMADAS").estc("0").longReferenceN("4").originBank("00330055")
                                .originBankName("BANCO FRANCES  ").presentation("CUOTAS").registerType("5")
                                .subAuthority("223").build();
                var auth4 = Authority.builder().admitsReservation("1").authority("88888").cuitNumber("20108496380")
                                .description("ENVIOS EXPRESS").estc("0").longReferenceN("5").originBank("00440055")
                                .originBankName("BANCO SANTANDER  ").presentation("MENSUALIDAD").registerType("5")
                                .subAuthority("250").build();
                authorities.put("20108496380", List.of(auth1, auth2, auth3, auth4));
        }

        private static List<CreditCardAdhesion> getCreditCardAdhesions() {
                CreditCardAdhesion credit1 = CreditCardAdhesion.builder().accountNumber("00000000571033854")
                                .adhesionFreezing("0").branch("057").branchEntry("034").ctl4("001").currency("080")
                                .entityNumber("00010").feeQuantity("000").invoiceControl("0")
                                .limitToControl("00999999999999999").referenceNumber("00142010000542")
                                .subentityNumber("002").build();

                CreditCardAdhesion credit2 = CreditCardAdhesion.builder().accountNumber("00000000571033854")
                                .adhesionFreezing("0").branch("057").branchEntry("034").ctl4("001").currency("080")
                                .entityNumber("00010").feeQuantity("000").invoiceControl("0")
                                .limitToControl("00999999999999999").referenceNumber("00142010001255")
                                .subentityNumber("002").build();

                CreditCardAdhesion credit3 = CreditCardAdhesion.builder().accountNumber("00000000571033854")
                                .adhesionFreezing("0").branch("057").branchEntry("034").ctl4("001").currency("080")
                                .entityNumber("00010").feeQuantity("000").invoiceControl("0")
                                .limitToControl("00999999999999999").referenceNumber("00172128001792")
                                .subentityNumber("002").build();

                return List.of(credit1, credit2, credit3);
        }

        private static Registration getRegistration() {
                Registration registration = null;
                Random random = new Random();
                int codigo = random.ints(250005000, 250009999).findFirst().getAsInt();
                int codigo2 = random.ints(1, 20).findFirst().getAsInt();
                if (codigo2 > 10) {
                        registration = Registration.builder().requestNumber(String.valueOf(codigo)).status("Derivado")
                                        .message("Registración derivada para ser autorizada por un operador.").build();
                } else {
                        registration = Registration.builder().requestNumber(String.valueOf(codigo)).status("Resuelto")
                                        .message("Registración correcta.").build();
                }

                return registration;
        }

        private void dormir(int tiempo) {
                try {
                        Thread.sleep(tiempo);
                } catch (InterruptedException e) {
                        System.out.println("Error: " + e.getMessage());
                }
        }

        private boolean validarTransaccional(String idTransaccional) {
                return List.of("REIMPRESION_TD", "BAJA_TARJETA_BANELCO").contains(idTransaccional);
        }

}