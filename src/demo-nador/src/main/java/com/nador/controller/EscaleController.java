package ma.portnador.escale.controller;

import jakarta.validation.Valid;
import ma.portnador.escale.exception.EscaleNotFoundException;
import ma.portnador.escale.model.Escale;
import ma.portnador.escale.model.StatutEscale;
import ma.portnador.escale.repository.EscaleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/escales")
public class EscaleController {

    private final EscaleRepository escaleRepository;

    public EscaleController(EscaleRepository escaleRepository) {
        this.escaleRepository = escaleRepository;
    }

    // GET /api/v1/escales?statut=EN_COURS&quai=Q3
    @GetMapping
    public List<Escale> getAll(
            @RequestParam(required = false) StatutEscale statut,
            @RequestParam(required = false) String quai) {

        if (statut != null) {
            return escaleRepository.findByStatut(statut);
        }
        if (quai != null) {
            return escaleRepository.findByQuai(quai);
        }
        return escaleRepository.findAll();
    }

    @GetMapping("/{id}")
    public Escale getById(@PathVariable Long id) {
        return escaleRepository.findById(id)
                .orElseThrow(() -> new EscaleNotFoundException(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Escale create(@Valid @RequestBody Escale escale) {
        return escaleRepository.save(escale);
    }

    @PutMapping("/{id}")
    public Escale update(@PathVariable Long id, @Valid @RequestBody Escale payload) {
        Escale escale = escaleRepository.findById(id)
                .orElseThrow(() -> new EscaleNotFoundException(id));

        escale.setNomNavire(payload.getNomNavire());
        escale.setNumeroImo(payload.getNumeroImo());
        escale.setDateArrivee(payload.getDateArrivee());
        escale.setDateDepart(payload.getDateDepart());
        escale.setQuai(payload.getQuai());
        escale.setStatut(payload.getStatut());
        escale.setTypeCargaison(payload.getTypeCargaison());

        return escaleRepository.save(escale);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!escaleRepository.existsById(id)) {
            throw new EscaleNotFoundException(id);
        }
        escaleRepository.deleteById(id);
    }
}
