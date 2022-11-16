package med.voll.api.dto;

import jakarta.validation.constraints.NotNull;
import med.voll.api.model.Endereco;

public record DadosAtualizacaoMedico(

        @NotNull
        Long id,

        String nome,

        String telefone,

        String email,

        Endereco endereco

    ) {
}
