package med.voll.api.domain.paciente;

import med.voll.api.domain.endereco.Endereco;

import javax.validation.constraints.NotNull;

public record DadosAtualizacaoPaciente(

        @NotNull
        Long id,

        String nome,

        String telefone,

        Endereco endereco

    ) {
}
