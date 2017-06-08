package project.model.dto.xml.flat;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import project.model.Row;

/**
 * author John Smith .
 */
@Root(name = "property")
public class FlatXmlPropertyRow implements Row {


    @Element(name = "key")
    private String key;
    @Element(name = "value")
    private String value;

    public FlatXmlPropertyRow(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public FlatXmlPropertyRow() {
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
