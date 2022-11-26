package med.voll.api.controller;

import io.jsonwebtoken.lang.Assert;
import med.voll.api.builder.PacienteBuilder;
import med.voll.api.model.Paciente;
import med.voll.api.model.Usuario;
import med.voll.api.repository.PacienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;


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