package com.ProyectoFinalAF.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;


@Getter
@Setter
@Entity
@Table(name="turnos")

public class Turno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    /*------Atributos------*/
    private LocalDate date;


    /*------Join con tabla Pacientes------*/
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "paciente_id",referencedColumnName = "id", nullable = false)
    private Paciente paciente;

    /*------Join con tabla Odontologos------*/
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "odontologo_id", referencedColumnName = "id", nullable = false)
    private Odontologo odontologo;



    /*------ToString------*/

    @Override
    public String toString() {
        return "Turno{" +
                "id=" + id +
                ", date=" + date +
                ", paciente=" + paciente +
                ", odontologo=" + odontologo +
                '}';
    }
}
