package med.voll.api.controller;

import med.voll.api.domain.medico.DadosDetalhamentoMedico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.medico.DadosAtualizacaoMedico;
import med.voll.api.domain.medico.DadosCadastroMedico;
import med.voll.api.domain.medico.DadosListagemMedicos;
import med.voll.api.domain.medico.Medico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

//Retorna sempre JSON como default
@RestController
@RequestMapping("medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    //Requisição Post que chegar na classe irá para este método
    //Ativa transação com DB
    //Este método precisa retornar os dados do médico criado, e a url de acesso ao método
    @PostMapping("cadastrar")
    @Transactional
    @CacheEvict(value = "listaDeMedicos", allEntries = true)
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder){
        //O parametro json será preenchido com o corpo da requisição
        var medico = new Medico(dados);
        medicoRepository.save(medico);
        //Cria uri para ser devolvida
        var uri = uriBuilder.path("/medicos/detalharDados/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }

    //PeagebleDefualt altera os dados originais de paginação
    @GetMapping("listar")
    @Cacheable(value = "listaDeMedicos")
    public ResponseEntity<Page<DadosListagemMedicos>>  listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable page){
        //O FindAll retorna médico, por isso é preciso converer usando o Stream
        var page1 = medicoRepository.findAllByStatusTrue(page).map(DadosListagemMedicos::new);//Converte para médico
        return ResponseEntity.ok(page1);//205
    }

    @PutMapping("atualizar")
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados){
        var medico = medicoRepository.getReferenceById(dados.id());//Busca a entidade pelo id
        medico.atualizarInformações(dados);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    //É preciso mapear o id na url
    //Este médico deleta sem excluir os dados do DB, apenas os desativa
    //Path direciona o caminho do id no navegador para a variável id do método
    @DeleteMapping("deletar/{id}")
    @CacheEvict(value = "listaDeMedicos", allEntries = true)
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id){
        var medico = medicoRepository.getReferenceById(id);
        medico.inativar();
        //Retorna na requisição o status 204
        //Com Response Entity é possível escolher o retorno
        return ResponseEntity.noContent().build();
    }

    //Este método reativa o médico
    @PutMapping("reativar/{id}")
    @Transactional
    public ResponseEntity reativar(@PathVariable Long id){
        var medico = medicoRepository.getReferenceById(id);
        medico.reativar();
        return ResponseEntity.noContent().build();//204
    }

    @GetMapping("detalharDados/{id}")
    public ResponseEntity detalharDados(@PathVariable Long id){
        var medico = medicoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));

    }

}
