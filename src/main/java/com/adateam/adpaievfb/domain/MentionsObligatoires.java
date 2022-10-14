package com.adateam.adpaievfb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MentionsObligatoires.
 */
@Entity
@Table(name = "mentions_obligatoires")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MentionsObligatoires implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "mention", nullable = false)
    private String mention;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @ManyToMany(mappedBy = "mentionsObligatoires")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "contrat", "employeur", "imposition", "cotisations", "mentionsObligatoires" }, allowSetters = true)
    private Set<FicheDePaie> ficheDePaies = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MentionsObligatoires id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMention() {
        return this.mention;
    }

    public MentionsObligatoires mention(String mention) {
        this.setMention(mention);
        return this;
    }

    public void setMention(String mention) {
        this.mention = mention;
    }

    public String getDescription() {
        return this.description;
    }

    public MentionsObligatoires description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<FicheDePaie> getFicheDePaies() {
        return this.ficheDePaies;
    }

    public void setFicheDePaies(Set<FicheDePaie> ficheDePaies) {
        if (this.ficheDePaies != null) {
            this.ficheDePaies.forEach(i -> i.removeMentionsObligatoires(this));
        }
        if (ficheDePaies != null) {
            ficheDePaies.forEach(i -> i.addMentionsObligatoires(this));
        }
        this.ficheDePaies = ficheDePaies;
    }

    public MentionsObligatoires ficheDePaies(Set<FicheDePaie> ficheDePaies) {
        this.setFicheDePaies(ficheDePaies);
        return this;
    }

    public MentionsObligatoires addFicheDePaie(FicheDePaie ficheDePaie) {
        this.ficheDePaies.add(ficheDePaie);
        ficheDePaie.getMentionsObligatoires().add(this);
        return this;
    }

    public MentionsObligatoires removeFicheDePaie(FicheDePaie ficheDePaie) {
        this.ficheDePaies.remove(ficheDePaie);
        ficheDePaie.getMentionsObligatoires().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MentionsObligatoires)) {
            return false;
        }
        return id != null && id.equals(((MentionsObligatoires) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MentionsObligatoires{" +
            "id=" + getId() +
            ", mention='" + getMention() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
