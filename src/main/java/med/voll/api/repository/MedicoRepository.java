package med.voll.api.repository;

import med.voll.api.dto.DadosAtualizacaoMedico;
import med.voll.api.model.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {


    public Long findById(DadosAtualizacaoMedico dados);

    Page<Medico> findAllByStatusTrue(Pageable page);
}
