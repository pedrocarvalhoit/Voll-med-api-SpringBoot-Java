package med.voll.api.dto;

import med.voll.api.model.Especialidade;
import med.voll.api.model.Medico;

public record DadosListagemMedicos(Long id,String nome, String email, String crm, Especialidade especialidade) {

    //Este construtor possibilita usar o medico repository
    public DadosListagemMedicos(Medico medico){
        this(medico.getId() ,medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade());
    }

}
