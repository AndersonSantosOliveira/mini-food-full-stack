package br.com.adsuema.minifood.infra;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
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
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(dadosTratarErros::new).toList());
    }

    // Login com e-mail inexistente ou senha errada.
    // A mensagem é a mesma nos dois casos, para não revelar quais e-mails têm conta.
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity tratarErro401() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new dadosMensagem("E-mail ou senha inválidos"));
    }

    private record dadosTratarErros(String campo, String mensagem) {
        public dadosTratarErros(FieldError fieldError) {
            this(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }

    private record dadosMensagem(String mensagem) {
    }

}

