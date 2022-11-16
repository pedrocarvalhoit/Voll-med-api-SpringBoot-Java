package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.dto.DadosCadastroMedico;
import med.voll.api.dto.DadosListagemMedicos;
import med.voll.api.model.Medico;
import med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Retorna sempre JSON como default
@RestController
@RequestMapping("medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    //Requisição Post que chegar na classe irá para este método
    @PostMapping("cadastro")
    //Ativa transação com DB
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados){
        //O parametro json será preenchido com o corpo da requisição
        medicoRepository.save(new Medico(dados));
    }

    @GetMapping("lista")
    //PeagebleDefualt altera os dados originais de paginação
    public Page<DadosListagemMedicos> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable page){

        //O FindAll retorna médico, por isso é preciso converer usando o Stream
        return medicoRepository.findAll(page)
                .map(DadosListagemMedicos::new);//Converte para médico


    }



}
