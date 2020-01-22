package com.marandu.testmono.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A OrigenEnergia.
 */
@Entity
@Table(name = "origen_energia")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OrigenEnergia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "estado")
    private Boolean estado;

    @OneToMany(mappedBy = "origenenergia")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Relevamiento> relevamientos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public OrigenEnergia nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean isEstado() {
        return estado;
    }

    public OrigenEnergia estado(Boolean estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Set<Relevamiento> getRelevamientos() {
        return relevamientos;
    }

    public OrigenEnergia relevamientos(Set<Relevamiento> relevamientos) {
        this.relevamientos = relevamientos;
        return this;
    }

    public OrigenEnergia addRelevamiento(Relevamiento relevamiento) {
        this.relevamientos.add(relevamiento);
        relevamiento.setOrigenenergia(this);
        return this;
    }

    public OrigenEnergia removeRelevamiento(Relevamiento relevamiento) {
        this.relevamientos.remove(relevamiento);
        relevamiento.setOrigenenergia(null);
        return this;
    }

    public void setRelevamientos(Set<Relevamiento> relevamientos) {
        this.relevamientos = relevamientos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrigenEnergia)) {
            return false;
        }
        return id != null && id.equals(((OrigenEnergia) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OrigenEnergia{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", estado='" + isEstado() + "'" +
            "}";
    }
}
