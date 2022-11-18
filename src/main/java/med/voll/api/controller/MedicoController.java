package med.voll.api.controller;

import med.voll.api.dto.DadosAtualizacaoMedico;
import med.voll.api.dto.DadosCadastroMedico;
import med.voll.api.dto.DadosListagemMedicos;
import med.voll.api.model.Medico;
import med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

//Retorna sempre JSON como default
@RestController
@RequestMapping("medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    //Requisição Post que chegar na classe irá para este método
    //Ativa transação com DB
    @PostMapping("cadastrar")
    @Transactional
    @CacheEvict(value = "listaDeMedicos", allEntries = true)
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados){
        //O parametro json será preenchido com o corpo da requisição
        medicoRepository.save(new Medico(dados));
    }

    //PeagebleDefualt altera os dados originais de paginação
    @GetMapping("listar")
    @Cacheable(value = "listaDeMedicos")
    public Page<DadosListagemMedicos> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable page){
        //O FindAll retorna médico, por isso é preciso converer usando o Stream
        return medicoRepository.findAllByStatusTrue(page).map(DadosListagemMedicos::new);//Converte para médico
    }

    @PutMapping("atualizar")
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados){
        var medico = medicoRepository.getReferenceById(dados.id());//Busca a entidade pelo id
        medico.atualizarInformações(dados);
    }

    //É preciso mapear o id na url
    //Este médico deleta sem excluir os dados do DB, apenas os desativa
    //Path direciona o caminho do id no navegador para a variável id do método
    @DeleteMapping("deletar/{id}")
    @CacheEvict(value = "listaDeMedicos", allEntries = true)
    @Transactional
    public void excluir(@PathVariable Long id){
        var medico = medicoRepository.getReferenceById(id);
        medico.inativar();
    }

    //Este médico reativa o médico
    @PutMapping("reativar/{id}")
    @Transactional
    public void reativar(@PathVariable Long id){
        var medico = medicoRepository.getReferenceById(id);
        medico.reativar();
    }

}
