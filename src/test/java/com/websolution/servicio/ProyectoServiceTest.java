package com.websolution.servicio;

import com.websolution.modelo.Proyecto;
import com.websolution.repositorio.ProyectoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProyectoServiceTest {

    @Mock
    private ProyectoRepository proyectoRepository;

    @InjectMocks
    private ProyectoService proyectoService;

    @Test
    void listarTodosDebeRetornarProyectos() {
        Proyecto proyecto = crearProyecto();

        when(proyectoRepository.findAll())
                .thenReturn(Arrays.asList(proyecto));

        assertEquals(1, proyectoService.listarTodos().size());
        verify(proyectoRepository).findAll();
    }

    @Test
    void buscarPorIdDebeRetornarProyecto() {
        Proyecto proyecto = crearProyecto();

        when(proyectoRepository.findById(1))
                .thenReturn(Optional.of(proyecto));

        Optional<Proyecto> resultado = proyectoService.buscarPorId(1);

        assertTrue(resultado.isPresent());
        assertEquals("Sistema WebSolution", resultado.get().getNombre());
    }

    @Test
    void guardarDebeRetornarProyectoGuardado() {
        Proyecto proyecto = crearProyecto();

        when(proyectoRepository.save(proyecto))
                .thenReturn(proyecto);

        Proyecto resultado = proyectoService.guardar(proyecto);

        assertNotNull(resultado);
        assertEquals("Sistema WebSolution", resultado.getNombre());
        verify(proyectoRepository).save(proyecto);
    }

    @Test
    void eliminarDebeLlamarAlRepositorio() {
        proyectoService.eliminar(1);

        verify(proyectoRepository).deleteById(1);
    }

    private Proyecto crearProyecto() {
        Proyecto proyecto = new Proyecto();
        proyecto.setId(1);
        proyecto.setNombre("Sistema WebSolution");
        proyecto.setDescripcion("Proyecto de prueba");
        proyecto.setEstado("En desarrollo");
        proyecto.setIdCliente(1);

        return proyecto;
    }
}