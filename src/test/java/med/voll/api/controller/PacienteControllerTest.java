package med.voll.api.controller;

import io.jsonwebtoken.lang.Assert;
import med.voll.api.domain.paciente.PacienteBuilder;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;


class PacienteControllerTest {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Test
    void testeDeletaPaciente(){
        Paciente paciente = new Paciente();
        paciente.inativar();
        paciente.reativar();
        Assert.isTrue(paciente.isStatus());
    }

    @Test
    void deveriaCadastrarPaciente(){
        Paciente paciente = new PacienteBuilder()
                .comNome("Pedro Duarte")
                .comEmail("959595")
                .comTelefone("pedro@gmmm.com")
                .comCpf("1231213")
                .criar();

        pacienteRepository.save(paciente);
        Paciente pacienteSalvo = pacienteRepository.getReferenceById(paciente.getId());
        Assert.notNull(pacienteSalvo);
    }

    @Test
    String testeListaPaciente(Pageable page){
        Paciente paciente = new Paciente();
        Paciente paciente2 = new Paciente();

        pacienteRepository.save(paciente2);
        pacienteRepository.save(paciente);

        pacienteRepository.findAllByStatusTrue(page);
        Assert.isTrue(paciente.isStatus());
        return "";
    }



}