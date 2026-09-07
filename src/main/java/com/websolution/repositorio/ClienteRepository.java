package com.websolution.repositorio;

import com.websolution.modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Cliente.
 *
 * Spring Data JPA proporciona automáticamente las operaciones
 * CRUD básicas como guardar, consultar, actualizar y eliminar.
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
