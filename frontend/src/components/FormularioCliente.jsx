import { useEffect, useState } from "react";

// Componente encargado de registrar y editar clientes.
// Se reutiliza para las historias de usuario HU-01 y HU-03.
function FormularioCliente({ clienteInicial, onGuardar, onCancelar }) {
  const [formulario, setFormulario] = useState({
    nombre: "",
    email: "",
    telefono: "",
    empresa: "",
  });

  const [error, setError] = useState("");

  // Carga los datos del cliente cuando se selecciona uno para editar.
  useEffect(() => {
    if (clienteInicial) {
      setFormulario({
        nombre: clienteInicial.nombre || "",
        email: clienteInicial.email || "",
        telefono: clienteInicial.telefono || "",
        empresa: clienteInicial.empresa || "",
      });
    } else {
      setFormulario({
        nombre: "",
        email: "",
        telefono: "",
        empresa: "",
      });
    }

    setError("");
  }, [clienteInicial]);

  // Actualiza el estado cuando el usuario modifica un campo.
  const manejarCambio = (evento) => {
    const { name, value } = evento.target;

    setFormulario({
      ...formulario,
      [name]: value,
    });
  };

  // Valida y envía la información del cliente.
  const manejarEnvio = (evento) => {
    evento.preventDefault();

    if (
      !formulario.nombre.trim() ||
      !formulario.email.trim() ||
      !formulario.telefono.trim() ||
      !formulario.empresa.trim()
    ) {
      setError("Todos los campos son obligatorios.");
      return;
    }

    if (!/^\d{10}$/.test(formulario.telefono)) {
      setError("El teléfono debe contener exactamente 10 números.");
      return;
    }

    setError("");
    onGuardar(formulario);
  };

  return (
    <form className="formulario-cliente" onSubmit={manejarEnvio}>
      <h2>{clienteInicial ? "Editar cliente" : "Registrar cliente"}</h2>

      <div className="campo">
        <label htmlFor="nombre">Nombre</label>
        <input
          id="nombre"
          name="nombre"
          type="text"
          value={formulario.nombre}
          onChange={manejarCambio}
          maxLength="100"
          placeholder="Nombre del cliente"
        />
      </div>

      <div className="campo">
        <label htmlFor="email">Correo electrónico</label>
        <input
          id="email"
          name="email"
          type="email"
          value={formulario.email}
          onChange={manejarCambio}
          maxLength="100"
          placeholder="correo@ejemplo.com"
        />
      </div>

      <div className="campo">
        <label htmlFor="telefono">Teléfono</label>
        <input
          id="telefono"
          name="telefono"
          type="text"
          value={formulario.telefono}
          onChange={manejarCambio}
          maxLength="10"
          placeholder="3001234567"
        />
      </div>

      <div className="campo">
        <label htmlFor="empresa">Empresa</label>
        <input
          id="empresa"
          name="empresa"
          type="text"
          value={formulario.empresa}
          onChange={manejarCambio}
          maxLength="100"
          placeholder="Nombre de la empresa"
        />
      </div>

      {error && <p className="mensaje-error">{error}</p>}

      <div className="acciones-formulario">
        <button type="submit">
          {clienteInicial ? "Actualizar" : "Registrar"}
        </button>

        {clienteInicial && (
          <button type="button" onClick={onCancelar}>
            Cancelar
          </button>
        )}
      </div>
    </form>
  );
}

export default FormularioCliente;
