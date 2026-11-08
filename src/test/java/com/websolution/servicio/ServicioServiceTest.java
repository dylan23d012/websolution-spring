package com.websolution.servicio;

import com.websolution.modelo.Servicio;
import com.websolution.repositorio.ServicioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServicioServiceTest {

    @Mock
    private ServicioRepository servicioRepository;

    @InjectMocks
    private ServicioService servicioService;

    @Test
    void listarTodosDebeRetornarServicios() {
        Servicio servicio = crearServicio();

        when(servicioRepository.findAll())
                .thenReturn(Arrays.asList(servicio));

        assertEquals(1, servicioService.listarTodos().size());
        verify(servicioRepository).findAll();
    }

    @Test
    void buscarPorIdDebeRetornarServicio() {
        Servicio servicio = crearServicio();

        when(servicioRepository.findById(1))
                .thenReturn(Optional.of(servicio));

        Optional<Servicio> resultado = servicioService.buscarPorId(1);

        assertTrue(resultado.isPresent());
        assertEquals("Diseño de página web", resultado.get().getNombre());
    }

    @Test
    void guardarDebeRetornarServicioGuardado() {
        Servicio servicio = crearServicio();

        when(servicioRepository.save(servicio))
                .thenReturn(servicio);

        Servicio resultado = servicioService.guardar(servicio);

        assertNotNull(resultado);
        assertEquals("Diseño de página web", resultado.getNombre());
        assertEquals(new BigDecimal("850000.00"), resultado.getPrecio());
        verify(servicioRepository).save(servicio);
    }

    @Test
    void eliminarDebeLlamarAlRepositorio() {
        servicioService.eliminar(1);

        verify(servicioRepository).deleteById(1);
    }

    private Servicio crearServicio() {
        Servicio servicio = new Servicio();
        servicio.setId(1);
        servicio.setNombre("Diseño de página web");
        servicio.setDescripcion("Diseño y desarrollo de páginas web");
        servicio.setPrecio(new BigDecimal("850000.00"));

        return servicio;
    }
}