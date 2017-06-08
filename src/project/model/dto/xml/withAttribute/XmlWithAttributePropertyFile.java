package project.model.dto.xml.withAttribute;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import project.model.dto.PropertyFileDto;

import java.util.List;

/**
 * author John Smith .
 */
@Root(name = "properties")
public class XmlWithAttributePropertyFile implements PropertyFileDto<XmlWithAttributePropertyRow> {

    @ElementList(inline = true)
    private List<XmlWithAttributePropertyRow> properties;

    @Override
    public List<XmlWithAttributePropertyRow> getProperties() {
        return properties;
    }

    @Override
    public void setProperties(List<XmlWithAttributePropertyRow> properties) {
        this.properties = properties;
    }
}