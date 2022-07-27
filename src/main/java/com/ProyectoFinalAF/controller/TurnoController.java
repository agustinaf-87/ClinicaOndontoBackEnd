package com.ProyectoFinalAF.controller;

import com.ProyectoFinalAF.DTO.TurnoDTO;
import com.ProyectoFinalAF.exceptions.BadRequestException;
import com.ProyectoFinalAF.exceptions.ResourceNotFoundException;
import com.ProyectoFinalAF.service.impl.TurnoServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("turnos")
public class TurnoController {


    @Autowired
    @Qualifier("TurnoServiceImpl")
    TurnoServiceImpl turnoService;



    /*------Registar turno------*/
    @PostMapping
    public ResponseEntity<TurnoDTO> guardar(@RequestBody TurnoDTO turnoDTO) throws BadRequestException, ResourceNotFoundException {
        return ResponseEntity.ok(turnoService.guardar(turnoDTO));
    }

    /*------Buscar turno por id------*/
    @GetMapping("/{id}")
    public ResponseEntity<TurnoDTO> buscar (@PathVariable Integer id) throws BadRequestException, ResourceNotFoundException{
        return ResponseEntity.ok(turnoService.buscar(id));
    }

    /*------Buscar todos los turnos------*/
    @GetMapping
    public ResponseEntity<List> buscarTodos ()throws ResourceNotFoundException{
        return ResponseEntity.ok(turnoService.buscarTodos());
    }

    /*------Actualizar turno ------*/
    @PutMapping
    public ResponseEntity<?> actualizar (@RequestBody TurnoDTO turnoDTO) throws ResourceNotFoundException, BadRequestException{
        return ResponseEntity.ok(turnoService.actualizar(turnoDTO));
    }

    /*------Eliminar turno por id------*/
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar (@PathVariable Integer id) throws ResourceNotFoundException{
        return ResponseEntity.ok(turnoService.eliminar(id));
    }

}
