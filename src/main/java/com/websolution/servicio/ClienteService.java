package com.websolution.servicio;

import com.websolution.modelo.Cliente;
import com.websolution.repositorio.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio de clientes.
 *
 * Contiene la lógica de negocio y utiliza el repositorio
 * proporcionado por Spring Data JPA para realizar el CRUD.
 */
@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    /**
     * Constructor que recibe el repositorio mediante inyección
     * de dependencias de Spring.
     */
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    /**
     * Consulta todos los clientes registrados.
     */
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    /**
     * Busca un cliente por su identificador.
     */
    public Optional<Cliente> buscarPorId(Integer id) {
        return clienteRepository.findById(id);
    }

    /**
     * Guarda un nuevo cliente o actualiza uno existente.
     */
    public Cliente guardar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    /**
     * Actualiza los datos de un cliente existente.
     */
    public Cliente actualizar(Integer id, Cliente datosCliente) {

        Optional<Cliente> clienteExistente =
                clienteRepository.findById(id);

        if (clienteExistente.isPresent()) {

            Cliente cliente = clienteExistente.get();

            cliente.setNombre(datosCliente.getNombre());
            cliente.setEmail(datosCliente.getEmail());
            cliente.setTelefono(datosCliente.getTelefono());
            cliente.setEmpresa(datosCliente.getEmpresa());

            return clienteRepository.save(cliente);
        }

        return null;
    }

    /**
     * Elimina un cliente mediante su identificador.
     */
    public boolean eliminar(Integer id) {

        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
