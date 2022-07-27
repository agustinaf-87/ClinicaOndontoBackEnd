package com.ProyectoFinalAF.service.impl;

import com.ProyectoFinalAF.DTO.DomicilioDTO;
import com.ProyectoFinalAF.configuration.ModelMapperConfiguration;
import com.ProyectoFinalAF.entities.Domicilio;
import com.ProyectoFinalAF.exceptions.ResourceNotFoundException;
import com.ProyectoFinalAF.repository.DomicilioRepository;
import com.ProyectoFinalAF.service.IService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("DomicilioServideImpl")
public class DomicilioServiceImpl  {

    @Autowired
    private DomicilioRepository domicilioRepository;


    @Autowired
    ModelMapperConfiguration mapper;

    public DomicilioDTO actualizar (DomicilioDTO domicilioDTO) throws ResourceNotFoundException {
        Optional<Domicilio> domicilioBD = domicilioRepository.findById(domicilioDTO.getId());
        if(domicilioBD.isPresent()){
            Domicilio domicilioActualizado = this.actualizar(domicilioBD.get(), domicilioDTO);
            Domicilio domicilioGuardado = domicilioRepository.save(domicilioActualizado);
            return mapper.getModelMapper().map(domicilioGuardado, DomicilioDTO.class);
        }
        else{
            throw new ResourceNotFoundException("El domicilio de id: " + domicilioDTO.getId()+ " no se encuentra en la BD ");
        }
    }

    private Domicilio actualizar (Domicilio domicilio, DomicilioDTO domicilioDTO){
        if (domicilioDTO.getCalle() != null){
            domicilio.setCalle(domicilioDTO.getCalle());
        }
        if(domicilioDTO.getNumero() !=null){
            domicilio.setNumero(domicilioDTO.getNumero());
        }
        if(domicilioDTO.getProvincia() != null){
            domicilio.setLocalidad(domicilio.getLocalidad());
        }
        if(domicilioDTO.getProvincia() != null){
            domicilio.setProvincia(domicilioDTO.getProvincia());
        }
        return domicilio;
    }

}
