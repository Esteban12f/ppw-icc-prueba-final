package ec.edu.ups.icc.labevaluation.supplies.exceptions;

import org.springframework.http.HttpStatus;

import ec.edu.ups.icc.labevaluation.core.exceptions.base.ApplicationException;

public class SupplyConflictException extends ApplicationException{

    protected SupplyConflictException(HttpStatus status, String code, String message) {
        super(status, code, message);
        //TODO Auto-generated constructor stub
    }
    
}
