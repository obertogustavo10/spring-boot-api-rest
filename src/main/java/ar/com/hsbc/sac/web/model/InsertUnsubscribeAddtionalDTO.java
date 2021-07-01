package ar.com.hsbc.sac.web.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class InsertUnsubscribeAddtionalDTO {
    private String operationId,
    proccess,
    requestNum,
    user, 
    nameUser,
    titDocType,
    titDoc,
    titName,
    docType,
    doc,
    name,
    codeAdmin,
    codecSucAdmin,
    numPartnet,
    numAcount,
    numPlastic,
    isBanca,
    indEst,
    causeCode,
    reasonCode,
    productCode,
    observation,
    phone,
    origen;
    
}
