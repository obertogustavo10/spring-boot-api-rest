package ar.com.hsbc.sac.web.app;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Registration {

    private String requestNumber;
    private String status;
    private String message;
    private String messageAddFiles;

}
