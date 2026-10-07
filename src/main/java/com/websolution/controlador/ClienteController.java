package com.websolution.controlador;

import com.websolution.modelo.Cliente;
import com.websolution.servicio.ClienteService;
import org.springframework.dao.DataIntegrityViolationException;
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
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    /**
     * Lista todos los clientes registrados.
     */
    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes() {
        return ResponseEntity.ok(clienteService.listarTodos());
    }

    /**
     * Busca un cliente por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarCliente(@PathVariable Integer id) {
        return clienteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Registra un nuevo cliente.
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
     * Si el cliente tiene proyectos asociados,
     * la base de datos impide su eliminación.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCliente(
            @PathVariable Integer id) {

        try {

            boolean eliminado = clienteService.eliminar(id);

            if (!eliminado) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("El cliente no existe.");
            }

            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();

        } catch (DataIntegrityViolationException e) {

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(
                        "No se puede eliminar el cliente porque tiene proyectos asociados."
                    );
        }
    }
}
