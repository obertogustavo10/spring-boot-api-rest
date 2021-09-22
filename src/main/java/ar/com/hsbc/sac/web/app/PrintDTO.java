package ar.com.hsbc.sac.web.app;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrintDTO {

    private String url;
    private String filename;
    private String fileExtension;
    private String path;
    private String size;

}
