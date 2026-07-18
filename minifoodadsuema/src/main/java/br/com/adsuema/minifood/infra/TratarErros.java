package br.com.adsuema.minifood.infra;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class TratarErros {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity tratar(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
    }

  @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex){
        var erros = ex.getFieldErrors();
       return ResponseEntity.badRequest().body(erros.stream().map(dadosTratarErros::new).toList());
  }
  private record dadosTratarErros(String campo, String mensagem){
        public dadosTratarErros(FieldError fieldError){
            this(fieldError.getField(),fieldError.getDefaultMessage());
        }
  }


}

