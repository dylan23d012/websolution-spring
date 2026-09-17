package com.websolution.controlador;

import com.websolution.modelo.Cliente;
import com.websolution.servicio.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controlador REST para la gestión de clientes.
 *
 * Expone los servicios CRUD mediante una API REST.
 */
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    /**
     * Constructor con inyección de dependencias.
     */
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    /**
     * Consulta todos los clientes.
     *
     * Método HTTP: GET
     * Ruta: /api/clientes
     */
    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes() {
        return ResponseEntity.ok(clienteService.listarTodos());
    }

    /**
     * Consulta un cliente por su ID.
     *
     * Método HTTP: GET
     * Ruta: /api/clientes/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarCliente(@PathVariable Integer id) {

        return clienteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.notFound().build());
    }

    /**
     * Registra un nuevo cliente.
     *
     * Método HTTP: POST
     * Ruta: /api/clientes
     */
    @PostMapping
    public ResponseEntity<Cliente> crearCliente(
            @Valid @RequestBody Cliente cliente) {

        Cliente nuevoCliente = clienteService.guardar(cliente);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nuevoCliente);
    }

    /**
     * Actualiza un cliente existente.
     *
     * Método HTTP: PUT
     * Ruta: /api/clientes/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(
            @PathVariable Integer id,
            @Valid @RequestBody Cliente cliente) {

        Cliente clienteActualizado =
                clienteService.actualizar(id, cliente);

        if (clienteActualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(clienteActualizado);
    }

    /**
     * Elimina un cliente.
     *
     * Método HTTP: DELETE
     * Ruta: /api/clientes/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(
            @PathVariable Integer id) {

        if (!clienteService.eliminar(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}