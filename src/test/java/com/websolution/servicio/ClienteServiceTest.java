package com.websolution.servicio;

import com.websolution.modelo.Cliente;
import com.websolution.repositorio.ClienteRepository;
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
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void listarTodosDebeRetornarClientes() {
        when(clienteRepository.findAll()).thenReturn(Arrays.asList(
            new Cliente("Juan", "juan@gmail.com", "3001234567", "Empresa A"),
            new Cliente("Ana", "ana@gmail.com", "3101234567", "Empresa B")
        ));

        assertEquals(2, clienteService.listarTodos().size());
        verify(clienteRepository).findAll();
    }

    @Test
    void buscarPorIdDebeRetornarCliente() {
        Cliente cliente = new Cliente("Juan", "juan@gmail.com", "3001234567", "Empresa A");
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));

        Optional<Cliente> resultado = clienteService.buscarPorId(1);

        assertTrue(resultado.isPresent());
        assertEquals("Juan", resultado.get().getNombre());
    }

    @Test
    void guardarDebeRetornarClienteGuardado() {
        Cliente cliente = new Cliente("Juan", "juan@gmail.com", "3001234567", "Empresa A");
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente resultado = clienteService.guardar(cliente);

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
        verify(clienteRepository).save(cliente);
    }

    @Test
    void actualizarDebeModificarClienteExistente() {
        Cliente existente = new Cliente("Juan", "juan@gmail.com", "3001234567", "Empresa A");
        Cliente datos = new Cliente("Carlos", "carlos@gmail.com", "3111234567", "Empresa B");

        when(clienteRepository.findById(1)).thenReturn(Optional.of(existente));
        when(clienteRepository.save(existente)).thenReturn(existente);

        Cliente resultado = clienteService.actualizar(1, datos);

        assertNotNull(resultado);
        assertEquals("Carlos", resultado.getNombre());
        assertEquals("carlos@gmail.com", resultado.getEmail());
        assertEquals("3111234567", resultado.getTelefono());
        assertEquals("Empresa B", resultado.getEmpresa());
    }

    @Test
    void actualizarDebeRetornarNullSiClienteNoExiste() {
        when(clienteRepository.findById(99)).thenReturn(Optional.empty());

        Cliente resultado = clienteService.actualizar(99,
            new Cliente("Carlos", "carlos@gmail.com", "3111234567", "Empresa B"));

        assertNull(resultado);
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    void eliminarDebeRetornarTrueSiClienteExiste() {
        when(clienteRepository.existsById(1)).thenReturn(true);

        boolean resultado = clienteService.eliminar(1);

        assertTrue(resultado);
        verify(clienteRepository).deleteById(1);
    }

    @Test
    void eliminarDebeRetornarFalseSiClienteNoExiste() {
        when(clienteRepository.existsById(99)).thenReturn(false);

        boolean resultado = clienteService.eliminar(99);

        assertFalse(resultado);
        verify(clienteRepository, never()).deleteById(99);
    }
}
