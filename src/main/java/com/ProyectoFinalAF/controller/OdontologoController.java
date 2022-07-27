package com.ProyectoFinalAF.controller;

import com.ProyectoFinalAF.DTO.OdontologoDTO;
import com.ProyectoFinalAF.exceptions.BadRequestException;
import com.ProyectoFinalAF.exceptions.ResourceNotFoundException;
import com.ProyectoFinalAF.service.impl.OdontologoServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController{

    @Autowired
    @Qualifier("OdontologoServiceImpl")
    OdontologoServiceImpl odontologoService;



    /*------Registar odontologo------*/
    @PostMapping
    public ResponseEntity<OdontologoDTO> guardar (@RequestBody OdontologoDTO odontologo){
        ResponseEntity<OdontologoDTO> res = ResponseEntity.badRequest().body(odontologo);
        OdontologoDTO odontologoRegistrado = odontologoService.guardar(odontologo);
        if (odontologoRegistrado != null){
            res = ResponseEntity.ok(odontologoRegistrado);
        }
        return res;
    }

    /*------Buscar odontologo por id------*/
    @GetMapping("/{id}")
    public ResponseEntity<OdontologoDTO>buscarOdontologo (@PathVariable Integer id) throws ResourceNotFoundException {
        return ResponseEntity.ok(odontologoService.buscar(id));
    }

    /*------Buscar todos los odontologos------*/
    @GetMapping
    public ResponseEntity<List<OdontologoDTO>> bucarTodos()throws ResourceNotFoundException {
        return ResponseEntity.ok(odontologoService.buscarTodos());
    }

    /*------Actualizar odontologo ------*/
    @PutMapping
    public ResponseEntity<String> actualizar (@RequestBody OdontologoDTO odontologo) throws BadRequestException, ResourceNotFoundException{
        ResponseEntity<String> res ;
        if(odontologo.getId() != null){
            res = ResponseEntity.ok(odontologoService.actualizar(odontologo));
        }else{
            throw new BadRequestException("No se encuentra el id del odontologo");
        }
        return res;
    }

    /*------Eliminar odontologo por id------*/
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar (@PathVariable Integer id) throws ResourceNotFoundException{
        return ResponseEntity.ok(odontologoService.eliminar(id));
    }







}