package ar.com.hsbc.sac.web.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Branch implements Comparable<Branch> {

    private String branch;
    private String numBranch;

    @Override
    public int compareTo(Branch o) {
        return Integer.compare(Integer.valueOf(getBranch()), Integer.valueOf(o.getBranch()));
    }

}
