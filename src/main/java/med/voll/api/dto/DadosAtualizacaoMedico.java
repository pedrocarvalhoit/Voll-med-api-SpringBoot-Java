package med.voll.api.dto;

import med.voll.api.model.Endereco;

import javax.validation.constraints.NotNull;

public record DadosAtualizacaoMedico(

        @NotNull
        Long id,

        String nome,

        String telefone,

        String email,

        Endereco endereco

    ) {
}
