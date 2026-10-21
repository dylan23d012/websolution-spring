package com.websolution.excepcion;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> manejarValidacion(
            MethodArgumentNotValidException ex) {

        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("estado", 400);
        respuesta.put("mensaje", "Los datos enviados no son válidos");

        Map<String, String> errores = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        error -> error.getField(),
                        error -> error.getDefaultMessage(),
                        (mensajeAnterior, mensajeNuevo) -> mensajeAnterior,
                        LinkedHashMap::new
                ));

        respuesta.put("errores", errores);
        return ResponseEntity.badRequest().body(respuesta);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> manejarSolicitudIncorrecta(
            HttpMessageNotReadableException ex) {

        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("estado", 400);
        respuesta.put("mensaje", "El formato de los datos enviados no es válido");

        return ResponseEntity.badRequest().body(respuesta);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> manejarEstadoHttp(
            ResponseStatusException ex) {

        HttpStatus estado = ex.getStatus();
        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("estado", estado.value());
        respuesta.put("mensaje", ex.getReason() != null
                ? ex.getReason()
                : "No se pudo procesar la solicitud");

        return ResponseEntity.status(estado).body(respuesta);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> manejarIntegridadDatos(
            DataIntegrityViolationException ex) {

        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("estado", 409);
        respuesta.put("mensaje",
                "No se puede completar la operación porque los datos están relacionados o incumplen una restricción");

        return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
    }
}
