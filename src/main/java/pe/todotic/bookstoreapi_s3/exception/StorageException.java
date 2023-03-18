package pe.todotic.bookstoreapi_s3.exception;

public class StorageException extends RuntimeException {

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable ex) {
        super(message, ex);
    }

}
