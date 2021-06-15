package ar.com.hsbc.sac.web.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class RelateFileDTO {
    private String operationId,
    requestNum;
    private String [] additionalRequestNumbers;
}
