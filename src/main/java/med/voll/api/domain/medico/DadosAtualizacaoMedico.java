package med.voll.api.domain.medico;

import med.voll.api.domain.endereco.Endereco;

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
