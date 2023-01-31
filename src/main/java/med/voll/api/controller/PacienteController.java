package med.voll.api.controller;

import med.voll.api.dto.DadosDetalhamentoPaciente;
import med.voll.api.repository.PacienteRepository;
import med.voll.api.dto.DadosAtualizacaoPaciente;
import med.voll.api.dto.DadosCadastroPaciente;
import med.voll.api.dto.DadosListagemPaciente;
import med.voll.api.model.Paciente;
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

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping("cadastrar")
    @CacheEvict(value = "listaDePacientes", allEntries = true)
    @Transactional
    public ResponseEntity cadastroPaciente(@RequestBody @Valid DadosCadastroPaciente dados, UriComponentsBuilder uriBuilder){
        var paciente = new Paciente(dados);
        pacienteRepository.save(paciente);
        var uri = uriBuilder.path("/pacientes/detalharDados/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));
    }

    @GetMapping("listar")
    @Cacheable(value = "listaDePacientes")
    public ResponseEntity<Page<DadosListagemPaciente>> listar(@PageableDefault(size = 10,sort = {"nome"}) Pageable page){
        var page1 = pacienteRepository.findAllByStatusTrue(page).map(DadosListagemPaciente::new);
        return ResponseEntity.ok(page1);

    }

    @PutMapping("atualizar")
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoPaciente dados){
        var paciente = pacienteRepository.getReferenceById(dados.id());
        paciente.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }

    @DeleteMapping("deletar/{id}")
    @CacheEvict(value = "listaDePacientes", allEntries = true)
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id){
        var paciente = pacienteRepository.getReferenceById(id);
        paciente.inativar();
        return ResponseEntity.noContent().build();
    }

    @PutMapping("reativar/{id}")
    @Transactional
    //Este médico reativa o médico
    public ResponseEntity reativar(@PathVariable Long id){
        var paciente = pacienteRepository.getReferenceById(id);
        paciente.reativar();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("detalharDados/{id}")
    public ResponseEntity detalharDados(@PathVariable Long id){
        var paciente = pacienteRepository.findById(id).map(DadosDetalhamentoPaciente::new);
        return ResponseEntity.ok(paciente);

    }

}
