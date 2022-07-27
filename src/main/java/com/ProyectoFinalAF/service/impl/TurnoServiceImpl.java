package com.ProyectoFinalAF.service.impl;


import com.ProyectoFinalAF.DTO.OdontologoDTO;
import com.ProyectoFinalAF.DTO.PacienteDTO;
import com.ProyectoFinalAF.DTO.TurnoDTO;
import com.ProyectoFinalAF.configuration.ModelMapperConfiguration;
import com.ProyectoFinalAF.entities.Turno;
import com.ProyectoFinalAF.exceptions.BadRequestException;
import com.ProyectoFinalAF.exceptions.ResourceNotFoundException;
import com.ProyectoFinalAF.repository.TurnoRepository;
import com.ProyectoFinalAF.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service("TurnoServiceImpl")
public class TurnoServiceImpl implements IService<TurnoDTO> {

    @Autowired
    TurnoRepository turnoRepository;

    @Autowired
    PacienteServiceImpl pacienteService;

    @Autowired
    OdontologoServiceImpl odontologoService;

    @Autowired
    ModelMapperConfiguration mapper;



    @Override
    public TurnoDTO guardar(TurnoDTO turno) throws BadRequestException, ResourceNotFoundException {
        TurnoDTO res ;
        if(turno.getPaciente().getId() != null && turno.getOdontologo() != null) {
            PacienteDTO pacienteBuscar = pacienteService.buscarPorId(turno.getPaciente().getId());
            OdontologoDTO odontodoBuscar = odontologoService.buscarPorId(turno.getOdontologo().getId());

            if (verificarTurnoDisponible(odontodoBuscar.getId(), turno.getDate())) {
                //Se crea en la base de datos
                Turno t = mapper.getModelMapper().map(turno, Turno.class);
                res = mapper.getModelMapper().map(turnoRepository.save(t), TurnoDTO.class);

                //Steamos el odontologo y paciente al JSON de la res
                res.setPaciente(pacienteBuscar);
                res.setOdontologo(odontodoBuscar);
            } else {
                throw new ResourceNotFoundException(" El odontologo con id: " + odontodoBuscar.getId() + "ya tiene un turno agendado en la fecha:  " + turno.getDate());
            }
        }else{
            throw new BadRequestException("Se debe ingresar el id del paciente/odontologo para crear correctamente el turno");
            }
    return res;
    }


    private Boolean verificarTurnoDisponible (Integer idOdontologo, LocalDate fechaTurno){
        boolean res = true;
        List<Turno> listaTurnos = turnoRepository.findAll();
        for(Turno t : listaTurnos){
            TurnoDTO turnoDTO = mapper.getModelMapper().map(t, TurnoDTO.class);
            if (turnoDTO.getOdontologo().getId().equals(idOdontologo) && turnoDTO.getDate().equals(fechaTurno)){
                res = false;
            }
        }
        return res;
    }

    @Override
    public TurnoDTO buscar(Integer id) throws ResourceNotFoundException {
        if(id == null){
            throw new ResourceNotFoundException("No hay un turno con ese ID");
        }
        return mapper.getModelMapper().map(turnoRepository.findById(id), TurnoDTO.class);
    }

    @Override
    public List<TurnoDTO> buscarTodos() throws ResourceNotFoundException {
        List<TurnoDTO> listTurnos = mapper.getModelMapper().map(turnoRepository.findAll(), List.class);
        if(listTurnos.size() <=0 ){
            throw new ResourceNotFoundException("No se encontro ningún turno cargado");
        }
        return listTurnos;
    }

    @Override
    public String actualizar(TurnoDTO turnoDTO) throws BadRequestException, ResourceNotFoundException {
        String res;
        if(turnoDTO.getId() != null){
            Optional<Turno> turnoActualizar= turnoRepository.findById(turnoDTO.getId());
            if(turnoActualizar.isPresent()){
                turnoRepository.save(this.actualizarTurnoBD(turnoActualizar.get(), turnoDTO));
                res = "Se actualizó el turno con id: " + turnoDTO.getId();

            }
            else{
                throw new ResourceNotFoundException("No se encontró el turno en la BD");
            }
        }else{
            throw new BadRequestException("No se cargo el id del turno que se quiere actualizar");
        }

        return res;
    }

    private Turno actualizarTurnoBD(Turno turno, TurnoDTO turnoDTO){
        if(turnoDTO.getDate() != null ){
            turno.setDate(turnoDTO.getDate());
        }
        return turno;
    }

    @Override
    public String eliminar(Integer id) throws ResourceNotFoundException {
        String res;
        if(turnoRepository.findById(id).isPresent()){
            turnoRepository.deleteById(id);
            res = "Se eliminó el turno con id: " + id;
        }
        else{
            throw new ResourceNotFoundException("No se encontró el turno en la BD");
        }

        return res;
    }
}
