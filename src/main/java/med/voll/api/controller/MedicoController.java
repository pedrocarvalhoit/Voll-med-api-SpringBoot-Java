package med.voll.api.controller;

import med.voll.api.medico.DadosCadastroMedico;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("medicos")
public class MedicoController {

    //Requisição Post que chegar na classe irá para este método
    @PostMapping
    public void cadastrarMedico(@RequestBody DadosCadastroMedico dados){
        //O parametro json será preenchido com o corpo da requisição
        System.out.println(dados);
    }

}
