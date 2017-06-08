package project.process;

import project.model.dto.PropertyFileDto;
import project.strategy.ImportStrategy;

import java.io.File;

/**
 * author John Smith .
 */
public class GetPropertyProcessor {

    private final ImportStrategy importStrategy;
    private final File propertyFile;

    public GetPropertyProcessor(File propertyFile, ImportStrategy importStrategy) {
        this.importStrategy = importStrategy;
        this.propertyFile = propertyFile;
    }

    public PropertyFileDto getPropertyFileFromFile() throws Exception {
        return importStrategy.getPropertyFileFromFile(propertyFile);
    }

}
