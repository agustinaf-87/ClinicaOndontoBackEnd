package com.ProyectoFinalAF.service;

import com.ProyectoFinalAF.exceptions.BadRequestException;
import com.ProyectoFinalAF.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IService<T> {
    T guardar(T t) throws BadRequestException, ResourceNotFoundException;
    T buscar(Integer id) throws BadRequestException, ResourceNotFoundException;
    List<T> buscarTodos() throws BadRequestException, ResourceNotFoundException;
    String actualizar(T t) throws BadRequestException, ResourceNotFoundException;
    String eliminar(Integer id) throws BadRequestException, ResourceNotFoundException;
}
