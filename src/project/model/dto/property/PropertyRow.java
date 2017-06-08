package project.model.dto.property;

import project.model.Row;

/**
 * author John Smith .
 */
public class PropertyRow implements Row {

    private String key;
    private String value;

    public PropertyRow(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }
}
