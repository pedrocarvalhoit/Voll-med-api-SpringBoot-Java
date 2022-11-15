package med.voll.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.model.Especialidade;

/**Classe RECORD que recebe as requisições evitando verbosidade
 * Precisa ter o mesmo nome dos atributos da requisicao*/

public record DadosCadastroMedico(
        //Anotações de validação
        //NOTBlank se usa apenas em String
        @NotBlank
        String nome,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String telefone,

        @NotBlank
        @Pattern(regexp = "\\d{4,6}")
        String crm,

        @NotNull
        Especialidade especialidade,

        @NotNull
        //Valida os dados deste outro objeto que também recebe dados
        @Valid
        DadosEndereco endereco) {
}
