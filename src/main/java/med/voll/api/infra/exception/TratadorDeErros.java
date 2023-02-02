package med.voll.api.infra.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;

//Classe que faz o tratamento de erros
@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(){
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity tratarErro500(SQLIntegrityConstraintViolationException ex){
        var errorMessage = ex.getLocalizedMessage();
        DadosErroValidacao errors = new DadosErroValidacao(errorMessage);
        return ResponseEntity.badRequest().body(errors.mensagem);
    }

    private record DadosErroValidacao(String mensagem){
        public DadosErroValidacao(String mensagem){
            this.mensagem = mensagem;
        }

        @Override
        public String mensagem() {
            return mensagem;
        }
    }

}
