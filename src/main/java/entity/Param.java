package entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Param {

    private String key;
    private String value;

    public Param(String key, String value) {
        this.key = key;
        this.value = value;
    }

}
