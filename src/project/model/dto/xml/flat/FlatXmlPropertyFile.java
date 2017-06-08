package project.model.dto.xml.flat;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import project.model.dto.PropertyFileDto;

import java.util.List;

/**
 * author John Smith .
 */
@Root(name = "properties")
public class FlatXmlPropertyFile implements PropertyFileDto<FlatXmlPropertyRow> {
    @ElementList(inline = true)
    private List<FlatXmlPropertyRow> properties;

    @Override
    public List<FlatXmlPropertyRow> getProperties() {
        return properties;
    }

    @Override
    public void setProperties(List<FlatXmlPropertyRow> properties) {
        this.properties = properties;
    }
}
