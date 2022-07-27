package com.ProyectoFinalAF.repository;


import com.ProyectoFinalAF.entities.Domicilio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DomicilioRepository extends JpaRepository<Domicilio,Integer> {

}
