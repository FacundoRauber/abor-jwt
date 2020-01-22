package com.marandu.testmono.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Relevamiento.
 */
@Entity
@Table(name = "relevamiento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Relevamiento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "escuela")
    private Boolean escuela;

    @Column(name = "puesto_salud")
    private Boolean puestoSalud;

    @Column(name = "estado")
    private Boolean estado;

    @ManyToOne
    @JsonIgnoreProperties("relevamientos")
    private Integrante integrante;

    @ManyToOne
    @JsonIgnoreProperties("relevamientos")
    private OrigenEnergia origenenergia;

    @ManyToOne
    @JsonIgnoreProperties("relevamientos")
    private OrigenAgua origenagua;

    @ManyToOne
    @JsonIgnoreProperties("relevamientos")
    private TipoServicio tiposervicio;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Relevamiento fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Boolean isEscuela() {
        return escuela;
    }

    public Relevamiento escuela(Boolean escuela) {
        this.escuela = escuela;
        return this;
    }

    public void setEscuela(Boolean escuela) {
        this.escuela = escuela;
    }

    public Boolean isPuestoSalud() {
        return puestoSalud;
    }

    public Relevamiento puestoSalud(Boolean puestoSalud) {
        this.puestoSalud = puestoSalud;
        return this;
    }

    public void setPuestoSalud(Boolean puestoSalud) {
        this.puestoSalud = puestoSalud;
    }

    public Boolean isEstado() {
        return estado;
    }

    public Relevamiento estado(Boolean estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Integrante getIntegrante() {
        return integrante;
    }

    public Relevamiento integrante(Integrante integrante) {
        this.integrante = integrante;
        return this;
    }

    public void setIntegrante(Integrante integrante) {
        this.integrante = integrante;
    }

    public OrigenEnergia getOrigenenergia() {
        return origenenergia;
    }

    public Relevamiento origenenergia(OrigenEnergia origenEnergia) {
        this.origenenergia = origenEnergia;
        return this;
    }

    public void setOrigenenergia(OrigenEnergia origenEnergia) {
        this.origenenergia = origenEnergia;
    }

    public OrigenAgua getOrigenagua() {
        return origenagua;
    }

    public Relevamiento origenagua(OrigenAgua origenAgua) {
        this.origenagua = origenAgua;
        return this;
    }

    public void setOrigenagua(OrigenAgua origenAgua) {
        this.origenagua = origenAgua;
    }

    public TipoServicio getTiposervicio() {
        return tiposervicio;
    }

    public Relevamiento tiposervicio(TipoServicio tipoServicio) {
        this.tiposervicio = tipoServicio;
        return this;
    }

    public void setTiposervicio(TipoServicio tipoServicio) {
        this.tiposervicio = tipoServicio;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Relevamiento)) {
            return false;
        }
        return id != null && id.equals(((Relevamiento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Relevamiento{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", escuela='" + isEscuela() + "'" +
            ", puestoSalud='" + isPuestoSalud() + "'" +
            ", estado='" + isEstado() + "'" +
            "}";
    }
}
