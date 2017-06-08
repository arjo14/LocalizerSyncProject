package project.strategy;

import project.model.dto.PropertyFileDto;

import java.io.File;

/**
 * author John Smith .
 */
public abstract class ImportStrategy {
    protected Class<? extends PropertyFileDto> fileType;

    public abstract PropertyFileDto getPropertyFileFromFile(File file) throws Exception;

    public ImportStrategy withFileType(Class<? extends PropertyFileDto> fileType) {
        this.fileType = fileType;
        return this;
    }
}
