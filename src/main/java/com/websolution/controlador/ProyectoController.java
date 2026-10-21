package com.websolution.controlador;

import com.websolution.modelo.Proyecto;
import com.websolution.repositorio.ClienteRepository;
import com.websolution.servicio.ProyectoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/proyectos")
@CrossOrigin(origins = "*")
public class ProyectoController {

    private final ProyectoService proyectoService;
    private final ClienteRepository clienteRepository;

    public ProyectoController(ProyectoService proyectoService,
                              ClienteRepository clienteRepository) {
        this.proyectoService = proyectoService;
        this.clienteRepository = clienteRepository;
    }

    @GetMapping
    public List<Proyecto> listar() {
        return proyectoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proyecto> buscar(@PathVariable Integer id) {
        return proyectoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Proyecto> crear(@Valid @RequestBody Proyecto proyecto) {
        validarCliente(proyecto.getIdCliente());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(proyectoService.guardar(proyecto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proyecto> actualizar(
            @PathVariable Integer id, @Valid @RequestBody Proyecto datos) {
        return proyectoService.buscarPorId(id).map(actual -> {
            actual.setNombre(datos.getNombre());
            actual.setDescripcion(datos.getDescripcion());
            actual.setFechaInicio(datos.getFechaInicio());
            actual.setFechaFin(datos.getFechaFin());
            actual.setEstado(datos.getEstado());
            actual.setIdCliente(datos.getIdCliente());
            validarCliente(actual.getIdCliente());
            return ResponseEntity.ok(proyectoService.guardar(actual));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!proyectoService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        proyectoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private void validarCliente(Integer idCliente) {
        if (idCliente == null || !clienteRepository.existsById(idCliente)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "El cliente indicado no existe");
        }
    }
}
