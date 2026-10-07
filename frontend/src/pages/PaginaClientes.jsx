import { useEffect, useState } from "react";

import FormularioCliente from "../components/FormularioCliente";
import ListaClientes from "../components/ListaClientes";
import MensajeValidacion from "../components/MensajeValidacion";

import {
  obtenerClientes,
  crearCliente,
  actualizarCliente,
  eliminarCliente,
} from "../services/clientesService";

// Página principal del módulo de gestión de clientes.
// Conecta los componentes React con el API REST de Spring Boot.

function PaginaClientes() {
  const [clientes, setClientes] = useState([]);
  const [clienteEditar, setClienteEditar] = useState(null);

  const [mensaje, setMensaje] = useState("");
  const [tipoMensaje, setTipoMensaje] = useState("exito");

  const [cargando, setCargando] = useState(true);

  // Carga los clientes al iniciar la página.
  useEffect(() => {
    cargarClientes();
  }, []);

  // Obtiene los clientes desde el API REST.
  const cargarClientes = async () => {
    try {
      setCargando(true);

      const datos = await obtenerClientes();

      console.log("CLIENTES RECIBIDOS DEL API:", datos);

      setClientes(datos);
    } catch (error) {
      console.error("Error al cargar clientes:", error);
      mostrarMensaje(`Error: ${error.message}`, "error");
    } finally {
      setCargando(false);
    }
  };

  // Registra o actualiza un cliente.
  const manejarGuardar = async (datosCliente) => {
    try {
      if (clienteEditar) {
        console.log("CLIENTE A ACTUALIZAR:", clienteEditar);
        console.log("ID A ACTUALIZAR:", clienteEditar.id);

        await actualizarCliente(clienteEditar.id, datosCliente);

        mostrarMensaje(
          "Cliente actualizado correctamente.",
          "exito"
        );

        setClienteEditar(null);
      } else {
        await crearCliente(datosCliente);

        mostrarMensaje(
          "Cliente registrado correctamente.",
          "exito"
        );
      }

      await cargarClientes();
    } catch (error) {
      console.error("Error al guardar cliente:", error);
      mostrarMensaje(`Error: ${error.message}`, "error");
    }
  };

  // Selecciona un cliente para editar.
  const manejarEditar = (cliente) => {
    console.log("CLIENTE SELECCIONADO PARA EDITAR:", cliente);
    console.log("ID DEL CLIENTE:", cliente.id);

    setClienteEditar(cliente);
    setMensaje("");
  };

  // Elimina un cliente.
  const manejarEliminar = async (idCliente) => {
    const confirmar = window.confirm(
      "¿Está seguro de eliminar este cliente?"
    );

    if (!confirmar) {
      return;
    }

    try {
      console.log("ID A ELIMINAR:", idCliente);

      await eliminarCliente(idCliente);

      mostrarMensaje(
        "Cliente eliminado correctamente.",
        "exito"
      );

      await cargarClientes();
    } catch (error) {
      console.error("Error al eliminar cliente:", error);
      mostrarMensaje(`Error: ${error.message}`, "error");
    }
  };

  // Cancela la edición del cliente.
  const manejarCancelar = () => {
    setClienteEditar(null);
    setMensaje("");
  };

  // Muestra mensajes temporales al usuario.
  const mostrarMensaje = (texto, tipo) => {
    setMensaje(texto);
    setTipoMensaje(tipo);

    setTimeout(() => {
      setMensaje("");
    }, 3000);
  };

  return (
    <main className="pagina-clientes">
      <section className="cabecera-clientes">
        <h1>WEB SOLUTION</h1>

        <h2>Sistema de Gestión de Clientes</h2>

        <p>
          Administra la información de los clientes de Web Solution.
        </p>
      </section>

      <MensajeValidacion
        tipo={tipoMensaje}
        mensaje={mensaje}
      />

      <div className="contenido-clientes">
        <FormularioCliente
          clienteInicial={clienteEditar}
          onGuardar={manejarGuardar}
          onCancelar={manejarCancelar}
        />

        {cargando ? (
          <p>Cargando clientes...</p>
        ) : (
          <ListaClientes
            clientes={clientes}
            onEditar={manejarEditar}
            onEliminar={manejarEliminar}
          />
        )}
      </div>
    </main>
  );
}

export default PaginaClientes;