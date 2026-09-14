package ro.hubstudentesc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class JobApplicationAlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public JobApplicationAlreadyExistsException() {
        super("Ai trimis deja o candidatura pentru acest anunt");
    }
}
