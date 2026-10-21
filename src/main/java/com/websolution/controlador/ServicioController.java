package com.websolution.controlador;

import com.websolution.modelo.Servicio;
import com.websolution.servicio.ServicioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/servicios")
@CrossOrigin(origins = "*")
public class ServicioController {

    private final ServicioService servicioService;

    public ServicioController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    @GetMapping
    public List<Servicio> listar() {
        return servicioService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servicio> buscar(@PathVariable Integer id) {
        return servicioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Servicio> crear(@Valid @RequestBody Servicio servicio) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(servicioService.guardar(servicio));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Servicio> actualizar(
            @PathVariable Integer id, @Valid @RequestBody Servicio datos) {
        return servicioService.buscarPorId(id).map(actual -> {
            actual.setNombre(datos.getNombre());
            actual.setDescripcion(datos.getDescripcion());
            actual.setPrecio(datos.getPrecio());
            return ResponseEntity.ok(servicioService.guardar(actual));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!servicioService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        servicioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
