package med.voll.api.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**Esta classe serve para possibilitar a consulta da senha pelo email */
@Repository
public interface UserRepository extends JpaRepository<Usuario, Long> {

    //Pode ser que o Spring não encontre email cadastrado, por isso o usuário fica como optional
    Optional<Usuario> findByEmail(String email);

}
