package com.marandu.testmono.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.marandu.testmono.domain.enumeration.Sexo;

/**
 * A Integrante.
 */
@Entity
@Table(name = "integrante")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Integrante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "dni", nullable = false)
    private Integer dni;

    @NotNull
    @Column(name = "apelllido", nullable = false)
    private String apelllido;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Min(value = 0)
    @Max(value = 140)
    @Column(name = "edad")
    private Integer edad;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexo")
    private Sexo sexo;

    @Column(name = "estado")
    private Boolean estado;

    @OneToMany(mappedBy = "integrante")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Relevamiento> relevamientos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("integrantes")
    private Comunidad comunidad;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDni() {
        return dni;
    }

    public Integrante dni(Integer dni) {
        this.dni = dni;
        return this;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public String getApelllido() {
        return apelllido;
    }

    public Integrante apelllido(String apelllido) {
        this.apelllido = apelllido;
        return this;
    }

    public void setApelllido(String apelllido) {
        this.apelllido = apelllido;
    }

    public String getNombre() {
        return nombre;
    }

    public Integrante nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public Integrante fechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
        return this;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getEdad() {
        return edad;
    }

    public Integrante edad(Integer edad) {
        this.edad = edad;
        return this;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public Integrante sexo(Sexo sexo) {
        this.sexo = sexo;
        return this;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public Boolean isEstado() {
        return estado;
    }

    public Integrante estado(Boolean estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Set<Relevamiento> getRelevamientos() {
        return relevamientos;
    }

    public Integrante relevamientos(Set<Relevamiento> relevamientos) {
        this.relevamientos = relevamientos;
        return this;
    }

    public Integrante addRelevamiento(Relevamiento relevamiento) {
        this.relevamientos.add(relevamiento);
        relevamiento.setIntegrante(this);
        return this;
    }

    public Integrante removeRelevamiento(Relevamiento relevamiento) {
        this.relevamientos.remove(relevamiento);
        relevamiento.setIntegrante(null);
        return this;
    }

    public void setRelevamientos(Set<Relevamiento> relevamientos) {
        this.relevamientos = relevamientos;
    }

    public Comunidad getComunidad() {
        return comunidad;
    }

    public Integrante comunidad(Comunidad comunidad) {
        this.comunidad = comunidad;
        return this;
    }

    public void setComunidad(Comunidad comunidad) {
        this.comunidad = comunidad;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Integrante)) {
            return false;
        }
        return id != null && id.equals(((Integrante) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Integrante{" +
            "id=" + getId() +
            ", dni=" + getDni() +
            ", apelllido='" + getApelllido() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", fechaNacimiento='" + getFechaNacimiento() + "'" +
            ", edad=" + getEdad() +
            ", sexo='" + getSexo() + "'" +
            ", estado='" + isEstado() + "'" +
            "}";
    }
}
