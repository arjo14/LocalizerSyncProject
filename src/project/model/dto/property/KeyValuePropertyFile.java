package project.model.dto.property;

import project.model.dto.PropertyFileDto;

import java.util.List;

/**
 * author John Smith .
 */
public class KeyValuePropertyFile implements PropertyFileDto<PropertyRow> {

    private List<PropertyRow> properties;

    public KeyValuePropertyFile(List<PropertyRow> properties) {
        this.properties = properties;
    }

    @Override
    public List<PropertyRow> getProperties() {
        return properties;
    }

    @Override
    public void setProperties(List<PropertyRow> properties) {
        this.properties = properties;
    }
}