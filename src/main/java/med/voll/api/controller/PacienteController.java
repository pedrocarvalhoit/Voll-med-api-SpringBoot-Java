package med.voll.api.controller;

import med.voll.api.dto.DadosAtualizacaoPaciente;
import med.voll.api.dto.DadosCadastroPaciente;
import med.voll.api.dto.DadosListagemPaciente;
import med.voll.api.model.Paciente;
import med.voll.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping("cadastrar")
    @CacheEvict(value = "listaDePacientes", allEntries = true)
    @Transactional
    public void cadastroPaciente(@RequestBody @Valid DadosCadastroPaciente dados){
        pacienteRepository.save(new Paciente(dados));
    }

    @GetMapping("listar")
    @Cacheable(value = "listaDePacientes")
    public Page<DadosListagemPaciente> listar(@PageableDefault(size = 10,sort = {"nome"}) Pageable page){
        return pacienteRepository.findAllByStatusTrue(page)
                .map(DadosListagemPaciente::new);

    }

    @PutMapping("atualizar")
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoPaciente dados){
        var paciente = pacienteRepository.getReferenceById(dados.id());
        paciente.atualizarInformacoes(dados);
    }

    @DeleteMapping("deletar/{id}")
    @CacheEvict(value = "listaDePacientes", allEntries = true)
    @Transactional
    public void excluir(@PathVariable Long id){
        var paciente = pacienteRepository.getReferenceById(id);
        paciente.inativar();
    }

    @PutMapping("reativar/{id}")
    @Transactional
    //Este médico reativa o médico
    public void reativar(@PathVariable Long id){
        var paciente = pacienteRepository.getReferenceById(id);
        paciente.reativar();
    }

}
