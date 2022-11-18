package med.voll.api.dto;

import med.voll.api.model.Endereco;

import javax.validation.constraints.NotNull;

public record DadosAtualizacaoPaciente(

        @NotNull
        Long id,

        String nome,

        String telefone,

        Endereco endereco

    ) {
}
