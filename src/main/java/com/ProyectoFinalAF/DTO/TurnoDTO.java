package com.ProyectoFinalAF.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TurnoDTO {
    private Integer id;
    private LocalDate date;
    private PacienteDTO paciente;
    private OdontologoDTO odontologo;
}
