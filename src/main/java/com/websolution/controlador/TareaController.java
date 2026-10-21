package com.websolution.controlador;

import com.websolution.modelo.Tarea;
import com.websolution.repositorio.ProyectoRepository;
import com.websolution.servicio.TareaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tareas")
@CrossOrigin(origins = "*")
public class TareaController {

    private final TareaService tareaService;
    private final ProyectoRepository proyectoRepository;

    public TareaController(TareaService tareaService,
                           ProyectoRepository proyectoRepository) {
        this.tareaService = tareaService;
        this.proyectoRepository = proyectoRepository;
    }

    @GetMapping
    public List<Tarea> listar() {
        return tareaService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarea> buscar(@PathVariable Integer id) {
        return tareaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Tarea> crear(@Valid @RequestBody Tarea tarea) {
        validarProyecto(tarea.getIdProyecto());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tareaService.guardar(tarea));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarea> actualizar(
            @PathVariable Integer id, @Valid @RequestBody Tarea datos) {
        return tareaService.buscarPorId(id).map(actual -> {
            actual.setNombre(datos.getNombre());
            actual.setDescripcion(datos.getDescripcion());
            actual.setEstado(datos.getEstado());
            actual.setFechaLimite(datos.getFechaLimite());
            actual.setIdProyecto(datos.getIdProyecto());
            validarProyecto(actual.getIdProyecto());
            return ResponseEntity.ok(tareaService.guardar(actual));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!tareaService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        tareaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private void validarProyecto(Integer idProyecto) {
        if (idProyecto == null || !proyectoRepository.existsById(idProyecto)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "El proyecto indicado no existe");
        }
    }
}
