package mmulla4.project2.uic.edu.treasuryservice;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by noman on 06/12/17.
 */

public class YearlyAvg {

    @JsonProperty("avg")
    private String avg;

    public String getAvg() {
        return avg;
    }

    public void setAvg(String avg) {
        this.avg = avg;
    }
}
