package ar.com.hsbc.sac.web.app;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ar.com.hsbc.sac.web.model.Authority;
import ar.com.hsbc.sac.web.model.Branch;
import ar.com.hsbc.sac.web.model.ClienteExtendidoDTO;
import ar.com.hsbc.sac.web.model.CreditCardAdhesion;
import ar.com.hsbc.sac.web.model.CuentaDTO;
import ar.com.hsbc.sac.web.model.ResponseMDW;

@RestController
@RequestMapping("/transaccional")
public class TransaccionalesController {

        private int contador = 1;
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
                System.out.println("Grabar Transaccional " + transactionalRequest.getCommonParams().getOption() + ": "
                                + transactionalRequest);
                System.out.println("Observaciones: " + transactionalRequest.getCommonParams().getObservation());
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
                Transaccional transaccional = Transaccional.builder().message(
                                "COMPLETAR TODOS LOS DATOS SOLICITADOS EN CADA CASO.\nVeriricar:\nQué?: Que la tarjeta esté activa.\nDónde?: En BRAP\nTiempo de Resolución: aproximadamente 10 días hábiles.\nSi la tarjeta está bliqueada entonces no podrá procesarse.")
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

        @GetMapping("/imprimir")
        public ResponseEntity<Transaccional> imprimir(@RequestParam String operationId,
                        @RequestParam String requestNumber, @RequestParam String opcion, @RequestParam String name) {
                System.out.println("######################## IMPRIMIR ");
                var fullFilename = opcion + "_" + requestNumber;
                PrintDTO print = PrintDTO.builder().url("http://webs.ucm.es/BUCM/servicios/doc5270.pdf")
                                .filename(fullFilename).fileExtension("pdf").build();
                Transaccional response = Transaccional.builder().print(print)
                                .header(UtilsController.getSuccessResponse()).build();

                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @GetMapping("/parametros/relacionesTipoDocumental")
        public ResponseEntity<Transaccional> consultarRelTipoDocumental(@RequestParam("operationId") String operationId,
                        @RequestParam("causeCode") String causeCode, @RequestParam("reasonCode") String reasonCode,
                        @RequestParam("documentType") String documentType,
                        @RequestParam("documentNumber") String documentNumber) {
                Transaccional transaccional = null;
                transaccional = Transaccional.builder().adjuntarArchivos(true)
                                .relTipoDocumentalCliente(new ArrayList<>())
                                .relTipoDocumentalProducto(getRelTipoDocumentalProducto())
                                .header(UtilsController.getSuccessResponse()).build();
                dormir(500);
                return new ResponseEntity<>(transaccional, HttpStatus.OK);
        }

        @GetMapping("/generarNumeroPedido")
        public ResponseEntity<Transaccional> generarNumeroPedido(@RequestParam("operationId") String operationId,
                        @RequestParam("companyCode") String companyCode, @RequestParam("causeCode") String causeCode) {
                Transaccional transaccional = Transaccional.builder()
                                .message(String.valueOf(generateRegistrationNumber()))
                                .header(UtilsController.getSuccessResponse()).build();
                dormir(2000);
                return new ResponseEntity<>(transaccional, HttpStatus.OK);
        }

        @PostMapping(value = "/uploadFile", consumes = { MediaType.APPLICATION_JSON_VALUE,
                        MediaType.MULTIPART_FORM_DATA_VALUE })
        public ResponseEntity<Transaccional> uploadFile(
                        @RequestPart(value = "file", required = false) MultipartFile file,
                        @RequestPart("attached") AttachFileDTO attached, HttpServletRequest request) {
                var serverFile = new File("C:\\log\\" + file.getOriginalFilename());
                try (var stream = new BufferedOutputStream(new FileOutputStream(serverFile))) {
                        byte[] bytes = file.getBytes();
                        stream.write(bytes);
                } catch (Exception e) {
                        System.out.println("Error");
                }
                dormir(100);
                return new ResponseEntity<>(Transaccional.builder().attached(attached)
                                .header(UtilsController.getSuccessResponse()).build(),
                                HttpStatus.INTERNAL_SERVER_ERROR);
        }

        private List<RelacionTipoDocumental> getRelTipoDocumentalCliente() {
                RelacionTipoDocumental rela = RelacionTipoDocumental.builder().causa("Pedidos")
                                .motivo("Modificación de Documento").tipoDocumental("Acta de Asamblea")
                                .clasificacion("Cliente").temporal("N").estado("A").codCausa("P").codMotivo("1114")
                                .codTipoDocumental("D001").codClasificacion("CLI").existeEnSAC(true).build();
                RelacionTipoDocumental rela1 = RelacionTipoDocumental.builder().causa("Pedidos")
                                .motivo("Modificación de Documento").tipoDocumental("Constancia de CUIL/CUIT")
                                .clasificacion("Cliente").temporal("N").estado("A").codCausa("P").codMotivo("1114")
                                .codTipoDocumental("D007").codClasificacion("CLI").build();
                RelacionTipoDocumental rela2 = RelacionTipoDocumental.builder().causa("Pedidos")
                                .motivo("Modificación de Documento").tipoDocumental("Copia de Documento")
                                .clasificacion("Cliente").temporal("N").estado("A").codCausa("P").codMotivo("1114")
                                .codTipoDocumental("D011").codClasificacion("CLI").existeEnSAC(true).build();
                return List.of(rela, rela1, rela2);
        }

        private List<RelacionTipoDocumental> getRelTipoDocumentalProducto() {
                RelacionTipoDocumental rela = RelacionTipoDocumental.builder().causa("Pedidos")
                                .motivo("Modificación de Documento").tipoDocumental("Mail de Autorización")
                                .clasificacion("Producto").temporal("S").estado("A").codCausa("P").codMotivo("1114")
                                .codTipoDocumental("D040").codClasificacion("PRO").build();
                RelacionTipoDocumental rela1 = RelacionTipoDocumental.builder().causa("Pedidos")
                                .motivo("Modificación de Documento").tipoDocumental("Printscreen de SAC (firmado)")
                                .clasificacion("Producto").temporal("N").estado("A").codCausa("P").codMotivo("1114")
                                .codTipoDocumental("D027").codClasificacion("PRO").build();
                RelacionTipoDocumental rela2 = RelacionTipoDocumental.builder().causa("Pedidos")
                                .motivo("Modificación de Documento").tipoDocumental("Copia de recibo")
                                .clasificacion("Producto").temporal("S").estado("A").codCausa("P").codMotivo("1114")
                                .codTipoDocumental("D040").codClasificacion("PRO").build();
                return List.of(rela, rela1, rela2);
        }

        private Map<String, String> obtenerDatosSolicitud(TransactionalRequest transactionalRequest) {
                Map<String, String> obtenerDatosSolicitud = new HashMap<>();
                obtenerDatosSolicitud.put("Número de Pedido",
                                transactionalRequest.getCommonParams().getRequestNumber());
                switch (transactionalRequest.getCommonParams().getOption()) {
                        case "ReimprimirTarjeta":
                        case "ReimprimirDiferida":
                                String destino = transactionalRequest.getReprintTdParams().getSucursal().equals("-")
                                                ? "Domicilio"
                                                : transactionalRequest.getReprintTdParams().getSucursal();
                                obtenerDatosSolicitud.put("Destino de reimpresión", destino);
                                break;
                        case "BajaDeTarjeta":
                                obtenerDatosSolicitud.put("Número de Tarjeta",
                                                transactionalRequest.getCommonParams().getProductNumber());
                                break;
                        default:
                                break;
                }

                return obtenerDatosSolicitud;
        }

        private String obtenerTitulo(String option) {
                var titulo = "";
                switch (option) {
                        case "ReimprimirTarjeta":
                                titulo = "Reimpresión Común de Tarjeta Banelco";
                                break;
                        case "ReimprimirDiferida":
                                titulo = "Reimpresión Diferida de Tarjeta Banelco";
                                break;
                        case "BajaDeTarjeta":
                                titulo = "Baja de Tarjeta Banelco";
                                break;
                        default:
                                break;
                }
                return titulo;
        }

        private List<Branch> getSucursales() {
                List<Branch> branches = new ArrayList<>();
                Set<Integer> sucus = new HashSet<>();
                for (int i = 0; i < 50; i++) {
                        Random random = new Random();
                        int codigo = random.ints(1, 500).findFirst().getAsInt();
                        if (!sucus.contains(codigo)) {
                                branches.add(Branch.builder().numBranch(String.valueOf(codigo))
                                                .branch("Sucursal " + codigo).build());
                                sucus.add(codigo);
                        }
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
                var auth1 = Authority.builder().admitsReservation("1").authority("88887").cuitNumber("20110083298")
                                .description("HUGO GUTIERREZ   ").estc("0").longReferenceN("7").originBank("00070999")
                                .originBankName("BANCO DE GALICIA").presentation("ALARMAS").registerType("5")
                                .subAuthority("195").build();
                var auth2 = Authority.builder().admitsReservation("1").authority("88887").cuitNumber("20110083298")
                                .description("AYS ALARMAS     ").estc("0").longReferenceN("6").originBank("00270055")
                                .originBankName("BANCO SUPERVIELLE  ").presentation("ABONO").registerType("5")
                                .subAuthority("600").build();
                authorities.put("20110083298", List.of(auth1, auth2));

                // 20110083298
                auth1 = Authority.builder().admitsReservation("1").authority("88881").cuitNumber("20108496380")
                                .description("JUAN GARCIA   ").estc("0").longReferenceN("4").originBank("00070999")
                                .originBankName("BANCO DE GALICIA Y BUENOS AIRES").presentation("HOGAR")
                                .registerType("5").subAuthority("200").build();
                auth2 = Authority.builder().admitsReservation("1").authority("88882").cuitNumber("20108496380")
                                .description("TOTALHOGAR").estc("0").longReferenceN("6").originBank("00270055")
                                .originBankName("BANCO SUPERVIELLE  ").presentation("CUOTA").registerType("5")
                                .subAuthority("250").build();
                var auth3 = Authority.builder().admitsReservation("1").authority("88883").cuitNumber("20108496380")
                                .description("COMPRAS PROGRAMADAS").estc("0").longReferenceN("1").originBank("00330055")
                                .originBankName("BANCO FRANCES  ").presentation("CUOTAS").registerType("5")
                                .subAuthority("223").build();
                var auth4 = Authority.builder().admitsReservation("1").authority("88884").cuitNumber("20108496380")
                                .description("ENVIOS EXPRESS").estc("0").longReferenceN("2").originBank("00440055")
                                .originBankName("BANCO SANTANDER  ").presentation("MENSUALIDAD").registerType("5")
                                .subAuthority("250").build();
                authorities.put("20108496380", List.of(auth1, auth2, auth3, auth4));
        }

        private static List<CreditCardAdhesion> getCreditCardAdhesions() {

                var auth1 = Authority.builder().admitsReservation("1").authority("88887").cuitNumber("20110083298")
                                .description("HUGO GUTIERREZ   ").estc("0").longReferenceN("7").originBank("00070999")
                                .originBankName("BANCO DE GALICIA").presentation("ALARMAS").registerType("5")
                                .subAuthority("195").build();

                var auth3 = Authority.builder().admitsReservation("1").authority("88888").cuitNumber("20108496380")
                                .description("COMPRAS PROGRAMADAS").estc("0").longReferenceN("4").originBank("00330055")
                                .originBankName("BANCO FRANCES  ").presentation("CUOTAS").registerType("5")
                                .subAuthority("223").build();

                CreditCardAdhesion credit1 = CreditCardAdhesion.builder().accountNumber("00000000571033854")
                                .adhesionFreezing("0").branch("057").branchEntry("034").ctl4("001").currency("080")
                                .entityNumber("00010").feeQuantity("000").invoiceControl("0")
                                .limitToControl("00999999999999999").referenceNumber("00142010000542")
                                .subentityNumber("002").authority(auth1).build();

                CreditCardAdhesion credit2 = CreditCardAdhesion.builder().accountNumber("00000000571033854")
                                .adhesionFreezing("0").branch("057").branchEntry("034").ctl4("001").currency("080")
                                .entityNumber("00010").feeQuantity("000").invoiceControl("0")
                                .limitToControl("00999999999999999").referenceNumber("00142010001255")
                                .subentityNumber("002").authority(auth1).build();

                CreditCardAdhesion credit3 = CreditCardAdhesion.builder().accountNumber("00000000571033854")
                                .adhesionFreezing("0").branch("057").branchEntry("034").ctl4("001").currency("080")
                                .entityNumber("00010").feeQuantity("000").invoiceControl("0")
                                .limitToControl("00999999999999999").referenceNumber("00172128001792")
                                .subentityNumber("002").authority(auth3).build();

                return List.of(credit1, credit2, credit3);
        }

        private static Registration getRegistration() {
                Registration registration = null;
                var random = new Random();
                int codigo = generateRegistrationNumber();
                var codigo2 = random.ints(1, 20).findFirst().getAsInt();
                if (codigo2 > 10) {
                        registration = Registration.builder().requestNumber(String.valueOf(codigo)).status("Derivado")
                                        .message("Registración derivada para ser autorizada por un operador.")
                                        .messageAddFiles("Error al grabar uno o más documentos adjuntos").build();
                } else {
                        registration = Registration.builder().requestNumber(String.valueOf(codigo)).status("Resuelto")
                                        .message("Registración correcta.").build();
                }

                return registration;
        }

        private static int generateRegistrationNumber() {
                var random = new Random();
                return random.ints(250005000, 250009999).findFirst().getAsInt();
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