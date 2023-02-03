package blog.entity;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionResponse {
    private HttpStatus httpStatus;
    private String errorMessage;

    public ExceptionResponse() {
    }

    public ExceptionResponse(HttpStatus httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "ExceptionHandlerRespinseEntity{" +
                "httpStatus=" + httpStatus +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
