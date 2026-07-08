package ma.portnador.escale.repository;

import ma.portnador.escale.model.Escale;
import ma.portnador.escale.model.StatutEscale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EscaleRepository extends JpaRepository<Escale, Long> {

    List<Escale> findByStatut(StatutEscale statut);

    List<Escale> findByQuai(String quai);
}
