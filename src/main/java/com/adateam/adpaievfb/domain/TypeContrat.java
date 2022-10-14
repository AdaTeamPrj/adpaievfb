package com.adateam.adpaievfb.domain;

import com.adateam.adpaievfb.domain.enumeration.TypeDeContrat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TypeContrat.
 */
@Entity
@Table(name = "type_contrat")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TypeContrat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type_contrat", nullable = false)
    private TypeDeContrat typeContrat;

    @OneToMany(mappedBy = "typeContrat")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "conventionCollective", "employeur", "employee", "typeContrat" }, allowSetters = true)
    private Set<Contrat> contrats = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_type_contrat__cotisation",
        joinColumns = @JoinColumn(name = "type_contrat_id"),
        inverseJoinColumns = @JoinColumn(name = "cotisation_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "baseTauxCotisation", "ficheDePaies", "typeContrats" }, allowSetters = true)
    private Set<Cotisation> cotisations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TypeContrat id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeDeContrat getTypeContrat() {
        return this.typeContrat;
    }

    public TypeContrat typeContrat(TypeDeContrat typeContrat) {
        this.setTypeContrat(typeContrat);
        return this;
    }

    public void setTypeContrat(TypeDeContrat typeContrat) {
        this.typeContrat = typeContrat;
    }

    public Set<Contrat> getContrats() {
        return this.contrats;
    }

    public void setContrats(Set<Contrat> contrats) {
        if (this.contrats != null) {
            this.contrats.forEach(i -> i.setTypeContrat(null));
        }
        if (contrats != null) {
            contrats.forEach(i -> i.setTypeContrat(this));
        }
        this.contrats = contrats;
    }

    public TypeContrat contrats(Set<Contrat> contrats) {
        this.setContrats(contrats);
        return this;
    }

    public TypeContrat addContrat(Contrat contrat) {
        this.contrats.add(contrat);
        contrat.setTypeContrat(this);
        return this;
    }

    public TypeContrat removeContrat(Contrat contrat) {
        this.contrats.remove(contrat);
        contrat.setTypeContrat(null);
        return this;
    }

    public Set<Cotisation> getCotisations() {
        return this.cotisations;
    }

    public void setCotisations(Set<Cotisation> cotisations) {
        this.cotisations = cotisations;
    }

    public TypeContrat cotisations(Set<Cotisation> cotisations) {
        this.setCotisations(cotisations);
        return this;
    }

    public TypeContrat addCotisation(Cotisation cotisation) {
        this.cotisations.add(cotisation);
        cotisation.getTypeContrats().add(this);
        return this;
    }

    public TypeContrat removeCotisation(Cotisation cotisation) {
        this.cotisations.remove(cotisation);
        cotisation.getTypeContrats().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeContrat)) {
            return false;
        }
        return id != null && id.equals(((TypeContrat) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeContrat{" +
            "id=" + getId() +
            ", typeContrat='" + getTypeContrat() + "'" +
            "}";
    }
}
