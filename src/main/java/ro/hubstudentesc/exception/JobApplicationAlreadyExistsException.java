package ro.hubstudentesc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class JobApplicationAlreadyExistsException extends RuntimeException {
    public JobApplicationAlreadyExistsException(){
        super("User has already applied to this job");
    }
}
