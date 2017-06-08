package project.strategy.impl;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import project.model.dto.PropertyFileDto;
import project.strategy.ImportStrategy;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * author John Smith .
 */
public class XmlImportStrategy extends ImportStrategy {
    @Override
    public PropertyFileDto getPropertyFileFromFile(File file) throws Exception {
        InputStream is =new FileInputStream(file);
        Serializer serializer = new Persister();
        return serializer.read(fileType, is);
    }
}
