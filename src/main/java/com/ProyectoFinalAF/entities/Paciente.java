package com.ProyectoFinalAF.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name="pacientes")

public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /*------Atributos------*/
    private String nombre;
    private String apellido;
    private String dni;
    private String sexo;
    private String telefono;
    private String obraSocial;
    private LocalDateTime fechaIngreso = LocalDateTime.now();

    /*------Join con tabla domicilio------*/
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="domicilio_id", referencedColumnName = "id")
    private Domicilio domicilio;

    /*------Join con la tabla ------*/
    @OneToMany(mappedBy = "paciente", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Turno> turnos= new HashSet<>();


    /*------ToString------*/

    @Override
    public String toString() {
        return "Paciente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni='" + dni + '\'' +
                ", fechaIngreso=" + fechaIngreso +
                ", domicilio=" + domicilio +
                ", turnos=" + turnos +
                '}';
    }
}
