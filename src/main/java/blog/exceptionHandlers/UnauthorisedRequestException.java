package blog.exceptionHandlers;

public class UnauthorisedRequestException extends Exception{
    public UnauthorisedRequestException() {
        super();
    }

    public UnauthorisedRequestException(String message) {
        super(message);
    }

    public UnauthorisedRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorisedRequestException(Throwable cause) {
        super(cause);
    }

    protected UnauthorisedRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
