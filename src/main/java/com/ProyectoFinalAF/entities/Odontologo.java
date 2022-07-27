package com.ProyectoFinalAF.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Setter
@Getter
@Entity
@Table(name= "odontologos")
public class Odontologo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /*------Atributos------*/
    private String nombre;
    private String apellido;
    private Integer matricula;
    private String telefono;


    /*------Join con tabla turnos------*/
    @OneToMany(mappedBy = "odontologo", fetch = FetchType.EAGER) //ver si pongo Lazy
    @JsonIgnore
    private Set<Turno> turnos = new HashSet<>();


    public Odontologo() {
    }

    public Odontologo(String nombre, String apellido, Integer matricula, String telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.matricula = matricula;
        this.telefono = telefono;
    }

    /*------ToString------*/
    @Override
    public String toString() {
        return "Odontologo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", matricula=" + matricula +
                ", turnos=" + turnos +
                '}';
    }
}
