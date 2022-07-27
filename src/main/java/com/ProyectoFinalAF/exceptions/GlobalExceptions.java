package com.ProyectoFinalAF.exceptions;



import com.ProyectoFinalAF.controller.OdontologoController;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptions {

    private static final Logger logger = Logger.getLogger(OdontologoController.class);

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<String> NotFound(ResourceNotFoundException e){
        logger.debug(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<String> BadRequest(BadRequestException ex) {
        logger.debug(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}
