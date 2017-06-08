package project.strategy.impl;

import project.model.dto.PropertyFileDto;
import project.model.dto.property.KeyValuePropertyFile;
import project.model.dto.property.PropertyRow;
import project.strategy.ImportStrategy;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * author John Smith .
 */
public class PropertyImportStrategy extends ImportStrategy {

    @Override
    public PropertyFileDto getPropertyFileFromFile(File file) throws Exception {

        try (InputStream input = new FileInputStream(file)) {
            Properties properties = new Properties();
            properties.load(input);

            List<PropertyRow> list = properties.entrySet()
                    .stream()
                    .map(entry -> new PropertyRow(entry.getKey().toString(), entry.getValue().toString()))
                    .collect(Collectors.toList());

            return new KeyValuePropertyFile(list);
        }
    }


}