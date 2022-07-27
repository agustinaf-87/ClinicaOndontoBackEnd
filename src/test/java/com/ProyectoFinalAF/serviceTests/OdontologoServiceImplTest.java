package com.ProyectoFinalAF.serviceTests;

import com.ProyectoFinalAF.DTO.OdontologoDTO;
import com.ProyectoFinalAF.exceptions.ResourceNotFoundException;
import com.ProyectoFinalAF.repository.OdontologoRepository;
import com.ProyectoFinalAF.service.impl.OdontologoServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class OdontologoServiceImplTest {

    @Autowired
    OdontologoServiceImpl odontologoServiceDTO;

    @Autowired
    OdontologoRepository odontologoRepository;

    private OdontologoDTO o;

    public void cargarDataSet () {
        o.setNombre("Guillermo");
        o.setApellido("Perez");
        o.setMatricula(123);
        odontologoServiceDTO.guardar(o);
    }

    @BeforeEach
    public void setUp(){
        o = new OdontologoDTO();
    }

    @Test
    public void borrarOdontologo() throws Exception {
        cargarDataSet();
        String respuestaEsperada = "Se eleminó con exito el odontologo";
        String res = odontologoServiceDTO.eliminar(1);
        Assert.assertEquals(respuestaEsperada, res);
    }

    @Test
    public void crearOdontologo() throws ResourceNotFoundException {
        //Dado
        int totalOdontologosAntes = odontologoRepository.findAll().size();
        int totalOdontologosEsperado = totalOdontologosAntes+1;


        //Cuando
        cargarDataSet();
        int totalOdontologosDespues = odontologoRepository.findAll().size();


        //Entonces
        Assert.assertEquals(totalOdontologosEsperado, totalOdontologosDespues);
    }

    @Test
    public void actualizarOdontologo() throws ResourceNotFoundException {
        //Dado
        cargarDataSet();
        OdontologoDTO original = odontologoServiceDTO.buscarPorId(1);

        //Cuando
        OdontologoDTO o = new OdontologoDTO();
        o.setId(1);
        o.setMatricula(1234);
        odontologoServiceDTO.actualizar(o);
        OdontologoDTO falseado = odontologoServiceDTO.buscarPorId(1);

        //Entonces
        Assert.assertFalse(original.equals(falseado));
    }

    @Test
    public void listarTodos() throws ResourceNotFoundException {
        //Dado
        cargarDataSet();
        cargarDataSet();
        cargarDataSet();
        int res = 3;

        //Cuando
        List<OdontologoDTO> odontologoDTOS = odontologoServiceDTO.buscarTodos();

        //Entonces
        Assert.assertEquals(res, odontologoDTOS.size());
    }
}
