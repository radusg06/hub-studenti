package ro.hubstudentesc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UnauthorizedSavedSearchException extends RuntimeException {
    public UnauthorizedSavedSearchException(){
        super("User cannot delete this search");
    }
}
