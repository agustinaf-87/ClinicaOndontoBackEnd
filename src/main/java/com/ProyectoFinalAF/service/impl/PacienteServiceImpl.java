package com.ProyectoFinalAF.service.impl;

import com.ProyectoFinalAF.DTO.DomicilioDTO;
import com.ProyectoFinalAF.DTO.PacienteDTO;
import com.ProyectoFinalAF.configuration.ModelMapperConfiguration;
import com.ProyectoFinalAF.entities.Domicilio;
import com.ProyectoFinalAF.entities.Paciente;
import com.ProyectoFinalAF.exceptions.BadRequestException;
import com.ProyectoFinalAF.exceptions.ResourceNotFoundException;
import com.ProyectoFinalAF.repository.PacienteRepository;
import com.ProyectoFinalAF.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service ("PacienteServiceImpl")
public class PacienteServiceImpl implements IService<PacienteDTO> {

    @Autowired
    PacienteRepository pacienteRepository;

    @Autowired
    ModelMapperConfiguration mapper;


    @Autowired
    DomicilioServiceImpl domicilioService;


    @Override
    public PacienteDTO guardar(PacienteDTO pacienteDTO) {
        Paciente paciente = mapper.getModelMapper().map(pacienteDTO, Paciente.class);
        pacienteRepository.save(paciente);
        return mapper.getModelMapper().map(pacienteRepository.save(paciente), PacienteDTO.class);
    }

    @Override
    public PacienteDTO buscar(Integer id) throws ResourceNotFoundException {
        if(id == null){
            throw new ResourceNotFoundException("No hay un paciente con ese ID");
        }
        return mapper.getModelMapper().map(pacienteRepository.findById(id), PacienteDTO.class);
    }

    public PacienteDTO buscarPorId(Integer id) throws ResourceNotFoundException {
        Paciente pacienteRespuesta = pacienteRepository.findById(id).orElse(null);
        if (pacienteRespuesta != null) {
            return mapper.getModelMapper().map(pacienteRespuesta, PacienteDTO.class);
        } else {
            throw new ResourceNotFoundException("No fue encontrado el paciente con id " + id);
        }
    }

        @Override
        public List<PacienteDTO> buscarTodos () throws BadRequestException {
            List<Paciente> listaPacientes = pacienteRepository.findAll();
            if (listaPacientes.size() == 0) {
                throw new BadRequestException("No existen pacientes cargados en la base de datos");
            }

            return mapper.getModelMapper().map(listaPacientes, List.class);
        }



    @Override
    public String actualizar(PacienteDTO pacienteDTO) throws ResourceNotFoundException {
        String res;
        Optional<Paciente> pacienteActualizar = pacienteRepository.findById(pacienteDTO.getId());
        if (pacienteActualizar.isPresent()) {
            pacienteRepository.save(this.actualizarPacienteBD(pacienteActualizar.get(), pacienteDTO));
            res = "Se actualizó el paciente con el id: " + pacienteDTO.getId();
        } else {
            throw new ResourceNotFoundException("No se pudo actualizar el odontologo. El odontologo con id " + pacienteDTO.getId() + " no se encontró en la BD");
        }
        return res;
    }


    private Paciente actualizarPacienteBD(Paciente paciente, PacienteDTO pacienteDTO) throws ResourceNotFoundException{
        if (pacienteDTO.getNombre() != null) {
            pacienteDTO.setNombre(pacienteDTO.getNombre());
        }
        if (pacienteDTO.getApellido() != null) {
            pacienteDTO.setApellido(pacienteDTO.getApellido());
        }

        if (pacienteDTO.getDni() != null) {
            paciente.setDni(pacienteDTO.getDni());

            if (pacienteDTO.getDomicilio() != null) {
                DomicilioDTO domicilioActualizado = domicilioService.actualizar(pacienteDTO.getDomicilio());
                paciente.setDomicilio(mapper.getModelMapper().map(domicilioActualizado, Domicilio.class));
            }

        }
        return paciente;

    }

    @Override
    public String eliminar(Integer id) throws  ResourceNotFoundException {
        String res;
        if (pacienteRepository.findById(id).isPresent()) {
            pacienteRepository.deleteById(id);
            res = "Se eleminó con exito el odontologo con el id: " + id;
        } else {
            throw new ResourceNotFoundException("No se pudo eliminar el odontologo, ya que el id " + id + " no fue encontrado en la BD");
        }
        return res;

    }
}
