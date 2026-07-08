package ma.portnador.escale.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "escales")
public class Escale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom du navire est obligatoire")
    @Column(nullable = false)
    private String nomNavire;

    @NotBlank(message = "L'immatriculation (IMO) est obligatoire")
    @Column(nullable = false, unique = true)
    private String numeroImo;

    @NotNull(message = "La date d'arrivée est obligatoire")
    @Column(nullable = false)
    private LocalDateTime dateArrivee;

    private LocalDateTime dateDepart;

    @NotBlank(message = "Le quai attribué est obligatoire")
    @Column(nullable = false)
    private String quai;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutEscale statut = StatutEscale.PREVUE;

    private String typeCargaison;

    public Escale() {
    }

    public Escale(String nomNavire, String numeroImo, LocalDateTime dateArrivee,
                  LocalDateTime dateDepart, String quai, String typeCargaison) {
        this.nomNavire = nomNavire;
        this.numeroImo = numeroImo;
        this.dateArrivee = dateArrivee;
        this.dateDepart = dateDepart;
        this.quai = quai;
        this.typeCargaison = typeCargaison;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomNavire() {
        return nomNavire;
    }

    public void setNomNavire(String nomNavire) {
        this.nomNavire = nomNavire;
    }

    public String getNumeroImo() {
        return numeroImo;
    }

    public void setNumeroImo(String numeroImo) {
        this.numeroImo = numeroImo;
    }

    public LocalDateTime getDateArrivee() {
        return dateArrivee;
    }

    public void setDateArrivee(LocalDateTime dateArrivee) {
        this.dateArrivee = dateArrivee;
    }

    public LocalDateTime getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(LocalDateTime dateDepart) {
        this.dateDepart = dateDepart;
    }

    public String getQuai() {
        return quai;
    }

    public void setQuai(String quai) {
        this.quai = quai;
    }

    public StatutEscale getStatut() {
        return statut;
    }

    public void setStatut(StatutEscale statut) {
        this.statut = statut;
    }

    public String getTypeCargaison() {
        return typeCargaison;
    }

    public void setTypeCargaison(String typeCargaison) {
        this.typeCargaison = typeCargaison;
    }
}
