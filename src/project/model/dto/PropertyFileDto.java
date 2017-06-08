package project.model.dto;

import project.model.Row;

import java.util.List;

/**
 * author John Smith .
 */
public interface PropertyFileDto<T extends Row> {

    List<T> getProperties();

    void setProperties(List<T> properties);

}
