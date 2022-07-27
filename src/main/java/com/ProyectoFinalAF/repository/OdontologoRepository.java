package com.ProyectoFinalAF.repository;


import com.ProyectoFinalAF.entities.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OdontologoRepository extends JpaRepository<Odontologo,Integer> {

}
