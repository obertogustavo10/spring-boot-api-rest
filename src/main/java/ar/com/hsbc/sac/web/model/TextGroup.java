package ar.com.hsbc.sac.web.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TextGroup {
    private String line1Text,
        line2Text,
        line3Text,
        line4Text,
        line5Text;
}
