package com.ProyectoFinalAF.controller;

import com.ProyectoFinalAF.DTO.PacienteDTO;
import com.ProyectoFinalAF.exceptions.BadRequestException;
import com.ProyectoFinalAF.exceptions.ResourceNotFoundException;
import com.ProyectoFinalAF.service.impl.PacienteServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    @Qualifier("PacienteServiceImpl")
    PacienteServiceImpl pacienteService;

    private static final Logger logger = Logger.getLogger(PacienteController.class);

    /*------Registar paciente------*/
    @PostMapping
    public ResponseEntity<PacienteDTO> guardar(@RequestBody PacienteDTO paciente){
        ResponseEntity<PacienteDTO> res = null;
        paciente.setFechaIngreso(LocalDateTime.now());
        PacienteDTO pacienteGuardado = pacienteService.guardar(paciente);
        if(pacienteGuardado != null){
            res = ResponseEntity.ok(pacienteGuardado);
        }
        return res;
    }

    /*------Buscar paciente por id------*/
    @GetMapping("/{id}")
    public ResponseEntity<PacienteDTO> buscarPaciente (@PathVariable Integer id) throws ResourceNotFoundException {
        return ResponseEntity.ok(pacienteService.buscar(id));
    }

    /*------Buscar todos los pacientes------*/
    @GetMapping
    public ResponseEntity<List<PacienteDTO>> buscarTodos () throws BadRequestException {
        return ResponseEntity.ok(pacienteService.buscarTodos());
    }

    /*------Actualizar paciente ------*/
    @PutMapping
    public ResponseEntity<String> actualizar (@RequestBody PacienteDTO paciente) throws BadRequestException, ResourceNotFoundException{
        ResponseEntity<String> res ;
        if(paciente.getId() != null){
            res = ResponseEntity.ok(pacienteService.actualizar(paciente));
        }else{
            throw new BadRequestException("No se encuentra el id del paciente");
        }
        return res;
    }
    /*------Eliminar paciente por id------*/
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar (@PathVariable Integer id) throws BadRequestException, ResourceNotFoundException {
        return ResponseEntity.ok(pacienteService.eliminar(id));

    }




}
