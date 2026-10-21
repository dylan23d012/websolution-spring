package com.websolution.servicio;

import com.websolution.modelo.Tarea;
import com.websolution.repositorio.TareaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TareaService {

    private final TareaRepository tareaRepository;

    public TareaService(TareaRepository tareaRepository) {
        this.tareaRepository = tareaRepository;
    }

    public List<Tarea> listarTodos() {
        return tareaRepository.findAll();
    }

    public Optional<Tarea> buscarPorId(Integer id) {
        return tareaRepository.findById(id);
    }

    public Tarea guardar(Tarea tarea) {
        return tareaRepository.save(tarea);
    }

    public void eliminar(Integer id) {
        tareaRepository.deleteById(id);
    }
}
