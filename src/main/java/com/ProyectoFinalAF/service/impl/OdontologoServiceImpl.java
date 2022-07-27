package com.ProyectoFinalAF.service.impl;

import com.ProyectoFinalAF.DTO.OdontologoDTO;
import com.ProyectoFinalAF.configuration.ModelMapperConfiguration;
import com.ProyectoFinalAF.entities.Odontologo;
import com.ProyectoFinalAF.exceptions.ResourceNotFoundException;
import com.ProyectoFinalAF.repository.OdontologoRepository;
import com.ProyectoFinalAF.service.IService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("OdontologoServiceImpl")
public class OdontologoServiceImpl implements IService<OdontologoDTO> {

    public static Logger logger = Logger.getLogger(OdontologoServiceImpl.class);

    @Autowired
    OdontologoRepository odontologoRepository;

    @Autowired
    ModelMapperConfiguration mapper;


    @Override
    public OdontologoDTO guardar(OdontologoDTO odontologoDTO) {
        Odontologo odontologo = mapper.getModelMapper().map(odontologoDTO, Odontologo.class);
        return mapper.getModelMapper().map(odontologoRepository.save(odontologo), OdontologoDTO.class);
    }

    @Override
    public OdontologoDTO buscar(Integer id) throws ResourceNotFoundException {
        if(id == null){
            throw new ResourceNotFoundException("No hay un odontologo con ese ID");
        }
        return mapper.getModelMapper().map(odontologoRepository.findById(id), OdontologoDTO.class);
    }


    public OdontologoDTO buscarPorId(Integer id) throws ResourceNotFoundException {
       Odontologo odontores = odontologoRepository.findById(id).orElse(null);
        if(odontores != null ){
            return mapper.getModelMapper().map(odontores, OdontologoDTO.class);
        }else{
            throw new ResourceNotFoundException ("No fue encontrado el odontologo con id " + id);
        }

    }


    @Override
    public List<OdontologoDTO> buscarTodos() throws ResourceNotFoundException {
        List<Odontologo> listaOdontologos = odontologoRepository.findAll();
        if(listaOdontologos.size() == 0){
            throw new ResourceNotFoundException("No existen odontólogos cargados en la base de datos");
        }
        return mapper.getModelMapper().map(listaOdontologos, List.class);
    }

    @Override
    public String actualizar(OdontologoDTO odontologoDTO) throws ResourceNotFoundException {
        String res;
        Optional<Odontologo> ondologoActualizar = odontologoRepository.findById(odontologoDTO.getId());
        if (ondologoActualizar.isPresent()){
            odontologoRepository.save(this.actualizarOdontoBD(ondologoActualizar .get(), odontologoDTO));
            res = "Se actualizó el odontologo con el id: " + odontologoDTO.getId();
        }else{
            throw new ResourceNotFoundException ("No se pudo actualizar el odontologo. El odontologo con id " + odontologoDTO.getId() + " no se encontró en la BD" );
        }
        return res;
    }

    private Odontologo actualizarOdontoBD(Odontologo odontologo, OdontologoDTO odontologoDTO){
        if(odontologoDTO.getNombre() != null){
            odontologo.setNombre(odontologoDTO.getNombre());
        }
        if(odontologoDTO.getApellido() != null){
            odontologo.setApellido(odontologoDTO.getApellido());
        }

        if(odontologoDTO.getMatricula() != null){
            odontologo.setMatricula(odontologoDTO.getMatricula());
        }
        return odontologo;
    }

    @Override
    public String eliminar(Integer id) throws ResourceNotFoundException {
        String res;
        if(odontologoRepository.findById(id).isPresent()){
            odontologoRepository.deleteById(id);
            res = "Se eleminó con exito el odontologo" ;
        }else{
            throw new ResourceNotFoundException("No se pudo eliminar el odontologo, ya que el id " + id + " no fue encontrado en la BD");
        }
        return res;
    }
}
