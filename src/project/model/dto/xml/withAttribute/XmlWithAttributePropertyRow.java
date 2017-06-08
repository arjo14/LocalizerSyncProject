package project.model.dto.xml.withAttribute;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;
import project.model.Row;

/**
 * author John Smith .
 */
@Root(name = "property")
public class XmlWithAttributePropertyRow implements Row {


    @Attribute
    private String key;
    @Text
    private String value;

    public XmlWithAttributePropertyRow() {
    }

    public XmlWithAttributePropertyRow(String key, String value) {
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
