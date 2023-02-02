package med.voll.api.domain.medico;

import med.voll.api.domain.medico.DadosAtualizacaoMedico;
import med.voll.api.domain.medico.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {


    public Long findById(DadosAtualizacaoMedico dados);

    Page<Medico> findAllByStatusTrue(Pageable page);

 //    public Medico findById();
}
