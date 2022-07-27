package com.ProyectoFinalAF.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PacienteDTO {
    private Integer id;
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String sexo;
    private String obraSocial;
    private LocalDateTime fechaIngreso;
    private DomicilioDTO domicilio;
}
