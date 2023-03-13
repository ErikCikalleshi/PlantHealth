package at.qe.backend.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Measurement {
    private Long id;
    private Integer plantID;
    private Double value;
    private String unit;
    private String type;
}
