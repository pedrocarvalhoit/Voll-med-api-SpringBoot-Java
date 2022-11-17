package med.voll.api.dto;

import jakarta.validation.constraints.NotNull;
import med.voll.api.model.Endereco;

public record DadosAtualizacaoPaciente(

        @NotNull
        Long id,

        String nome,

        String telefone,

        Endereco endereco

    ) {
}
