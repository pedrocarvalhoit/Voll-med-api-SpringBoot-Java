package med.voll.api.domain.medico;

import med.voll.api.domain.endereco.DadosEndereco;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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
