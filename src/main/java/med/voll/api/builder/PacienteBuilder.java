package med.voll.api.builder;

import med.voll.api.model.Paciente;

public class PacienteBuilder {

    String nome;
    String email;
    String telefone;
    String cpf;

    public PacienteBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public PacienteBuilder comEmail( String email) {
        this.email = email;
        return this;
    }

    public PacienteBuilder comTelefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public PacienteBuilder comCpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public Paciente criar(){
        return new Paciente(nome, email, telefone, cpf);
    }
}
