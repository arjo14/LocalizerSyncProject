package project.exception;

/**
 * author John Smith .
 */
public class FileTypeNotSupportedException extends Exception{

    @Override
    public String getMessage() {
        return "File type not supported";
    }
}