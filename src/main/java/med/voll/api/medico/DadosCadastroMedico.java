package med.voll.api.medico;

import med.voll.api.endereco.DadosEndereco;

/**Classe que recebe as requisições evitando verbosidade
 * Precisa ter o mesmo nome dos atributos da requisicao*/

public record DadosCadastroMedico(String nome, String email, String crm, Especialidade especialidade,
                                  DadosEndereco endereco) {
}
