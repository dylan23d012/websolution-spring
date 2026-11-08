package com.websolution.servicio;

import com.websolution.modelo.Tarea;
import com.websolution.repositorio.TareaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TareaServiceTest {

    @Mock
    private TareaRepository tareaRepository;

    @InjectMocks
    private TareaService tareaService;

    @Test
    void listarTodosDebeRetornarTareas() {
        Tarea tarea = crearTarea();

        when(tareaRepository.findAll())
                .thenReturn(Arrays.asList(tarea));

        assertEquals(1, tareaService.listarTodos().size());
        verify(tareaRepository).findAll();
    }

    @Test
    void buscarPorIdDebeRetornarTarea() {
        Tarea tarea = crearTarea();

        when(tareaRepository.findById(1))
                .thenReturn(Optional.of(tarea));

        Optional<Tarea> resultado = tareaService.buscarPorId(1);

        assertTrue(resultado.isPresent());
        assertEquals("Diseñar página principal", resultado.get().getNombre());
    }

    @Test
    void guardarDebeRetornarTareaGuardada() {
        Tarea tarea = crearTarea();

        when(tareaRepository.save(tarea))
                .thenReturn(tarea);

        Tarea resultado = tareaService.guardar(tarea);

        assertNotNull(resultado);
        assertEquals("Diseñar página principal", resultado.getNombre());
        verify(tareaRepository).save(tarea);
    }

    @Test
    void eliminarDebeLlamarAlRepositorio() {
        tareaService.eliminar(1);

        verify(tareaRepository).deleteById(1);
    }

    private Tarea crearTarea() {
        Tarea tarea = new Tarea();
        tarea.setId(1);
        tarea.setNombre("Diseñar página principal");
        tarea.setDescripcion("Crear el diseño de la página principal");
        tarea.setEstado("Pendiente");
        tarea.setFechaLimite(LocalDate.of(2026, 11, 15));
        tarea.setIdProyecto(1);

        return tarea;
    }
}