import { useEffect, useState } from "react";
import { obtenerClientes } from "../services/clientesService";
import {
  obtenerProyectos,
  crearProyecto,
  actualizarProyecto,
  eliminarProyecto,
} from "../services/proyectosService";

const formularioVacio = {
  nombre: "",
  descripcion: "",
  fechaInicio: "",
  fechaFin: "",
  estado: "Pendiente",
  idCliente: "",
};

function PaginaProyectos() {
  const [proyectos, setProyectos] = useState([]);
  const [clientes, setClientes] = useState([]);
  const [formulario, setFormulario] = useState(formularioVacio);
  const [proyectoEditar, setProyectoEditar] = useState(null);
  const [cargando, setCargando] = useState(true);
  const [mensaje, setMensaje] = useState("");
  const [tipoMensaje, setTipoMensaje] = useState("exito");

  useEffect(() => {
    cargarDatos();
  }, []);

  async function cargarDatos() {
    try {
      setCargando(true);
      const [listaProyectos, listaClientes] = await Promise.all([
        obtenerProyectos(),
        obtenerClientes(),
      ]);
      setProyectos(listaProyectos);
      setClientes(listaClientes);
    } catch (error) {
      mostrarMensaje(`No se pudieron cargar los datos: ${error.message}`, "error");
    } finally {
      setCargando(false);
    }
  }

  function mostrarMensaje(texto, tipo = "exito") {
    setMensaje(texto);
    setTipoMensaje(tipo);
  }

  function manejarCambio(evento) {
    const { name, value } = evento.target;
    setFormulario((anterior) => ({ ...anterior, [name]: value }));
  }

  async function manejarGuardar(evento) {
    evento.preventDefault();

    if (!formulario.nombre.trim()) {
      mostrarMensaje("Escribe el nombre del proyecto.", "error");
      return;
    }

    if (!formulario.idCliente) {
      mostrarMensaje("Selecciona un cliente para el proyecto.", "error");
      return;
    }

    if (
      formulario.fechaInicio &&
      formulario.fechaFin &&
      formulario.fechaFin < formulario.fechaInicio
    ) {
      mostrarMensaje("La fecha de entrega no puede ser anterior a la fecha de inicio.", "error");
      return;
    }

    const datos = {
      nombre: formulario.nombre.trim(),
      descripcion: formulario.descripcion.trim(),
      fechaInicio: formulario.fechaInicio || null,
      fechaFin: formulario.fechaFin || null,
      estado: formulario.estado,
      idCliente: Number(formulario.idCliente),
    };

    try {
      if (proyectoEditar) {
        await actualizarProyecto(proyectoEditar.id, datos);
        mostrarMensaje("Proyecto actualizado correctamente.");
      } else {
        await crearProyecto(datos);
        mostrarMensaje("Proyecto registrado correctamente.");
      }

      setFormulario(formularioVacio);
      setProyectoEditar(null);
      await cargarDatos();
    } catch (error) {
      mostrarMensaje(`No se pudo guardar: ${error.message}`, "error");
    }
  }

  function manejarEditar(proyecto) {
    setProyectoEditar(proyecto);
    setFormulario({
      nombre: proyecto.nombre || "",
      descripcion: proyecto.descripcion || "",
      fechaInicio: proyecto.fechaInicio || "",
      fechaFin: proyecto.fechaFin || "",
      estado: proyecto.estado || "Pendiente",
      idCliente: String(proyecto.idCliente ?? ""),
    });
    setMensaje("");
    window.location.hash = "proyectos";
    window.scrollTo({ top: 0, behavior: "smooth" });
  }

  function manejarCancelar() {
    setProyectoEditar(null);
    setFormulario(formularioVacio);
    setMensaje("");
  }

  async function manejarEliminar(proyecto) {
    const confirmar = window.confirm(
      `¿Seguro que deseas eliminar el proyecto "${proyecto.nombre}"?`
    );

    if (!confirmar) return;

    try {
      await eliminarProyecto(proyecto.id);
      mostrarMensaje("Proyecto eliminado correctamente.");
      await cargarDatos();
    } catch (error) {
      mostrarMensaje(`No se pudo eliminar: ${error.message}`, "error");
    }
  }

  function nombreCliente(idCliente) {
    const cliente = clientes.find((item) => item.id === idCliente);
    return cliente ? cliente.nombre : `Cliente #${idCliente}`;
  }

  return (
    <main className="pagina-clientes" id="proyectos">
      <section className="cabecera-clientes">
        <h1>Gestión de Proyectos</h1>
        <p>Organiza los proyectos de diseño y desarrollo web de Web Solution.</p>
      </section>

      {mensaje && (
        <div
          className={tipoMensaje === "error" ? "mensaje-error" : "mensaje-exito"}
          role="status"
        >
          {mensaje}
        </div>
      )}

      <section className="formulario-cliente">
        <h2>{proyectoEditar ? "Actualizar proyecto" : "Registrar proyecto"}</h2>

        {clientes.length === 0 && !cargando && (
          <p>Primero debes registrar al menos un cliente en el módulo Clientes.</p>
        )}

        <form onSubmit={manejarGuardar}>
          <div className="campo">
            <label htmlFor="nombreProyecto">Nombre del proyecto *</label>
            <input
              id="nombreProyecto"
              name="nombre"
              value={formulario.nombre}
              onChange={manejarCambio}
              maxLength={100}
              required
            />
          </div>

          <div className="campo">
            <label htmlFor="clienteProyecto">Cliente *</label>
            <select
              id="clienteProyecto"
              name="idCliente"
              value={formulario.idCliente}
              onChange={manejarCambio}
              required
            >
              <option value="">Selecciona un cliente</option>
              {clientes.map((cliente) => (
                <option key={cliente.id} value={cliente.id}>
                  {cliente.nombre}
                </option>
              ))}
            </select>
          </div>

          <div className="campo">
            <label htmlFor="descripcionProyecto">Descripción</label>
            <textarea
              id="descripcionProyecto"
              name="descripcion"
              value={formulario.descripcion}
              onChange={manejarCambio}
              rows={3}
            />
          </div>

          <div className="campo">
            <label htmlFor="fechaInicio">Fecha de inicio</label>
            <input
              id="fechaInicio"
              name="fechaInicio"
              type="date"
              value={formulario.fechaInicio}
              onChange={manejarCambio}
            />
          </div>

          <div className="campo">
            <label htmlFor="fechaFin">Fecha de entrega</label>
            <input
              id="fechaFin"
              name="fechaFin"
              type="date"
              value={formulario.fechaFin}
              onChange={manejarCambio}
            />
          </div>

          <div className="campo">
            <label htmlFor="estadoProyecto">Estado</label>
            <select
              id="estadoProyecto"
              name="estado"
              value={formulario.estado}
              onChange={manejarCambio}
            >
              <option value="Pendiente">Pendiente</option>
              <option value="En proceso">En proceso</option>
              <option value="Finalizado">Finalizado</option>
            </select>
          </div>

          <div className="acciones-formulario">
            <button type="submit" disabled={cargando || clientes.length === 0}>
              {proyectoEditar ? "Guardar cambios" : "Registrar proyecto"}
            </button>

            {proyectoEditar && (
              <button type="button" onClick={manejarCancelar}>
                Cancelar
              </button>
            )}
          </div>
        </form>
      </section>

      <section className="lista-clientes">
        <h2>Proyectos registrados</h2>

        {cargando ? (
          <p>Cargando proyectos...</p>
        ) : proyectos.length === 0 ? (
          <p>Aún no hay proyectos registrados.</p>
        ) : (
          <div style={{ overflowX: "auto" }}>
            <table style={{ width: "100%", borderCollapse: "collapse", background: "#fff" }}>
              <thead>
                <tr>
                  <th style={{ padding: 10, textAlign: "left" }}>Proyecto</th>
                  <th style={{ padding: 10, textAlign: "left" }}>Cliente</th>
                  <th style={{ padding: 10, textAlign: "left" }}>Inicio</th>
                  <th style={{ padding: 10, textAlign: "left" }}>Entrega</th>
                  <th style={{ padding: 10, textAlign: "left" }}>Estado</th>
                  <th style={{ padding: 10, textAlign: "left" }}>Acciones</th>
                </tr>
              </thead>
              <tbody>
                {proyectos.map((proyecto) => (
                  <tr key={proyecto.id}>
                    <td style={{ padding: 10, borderTop: "1px solid #e2e8f0" }}>
                      <strong>{proyecto.nombre}</strong>
                      {proyecto.descripcion && <p>{proyecto.descripcion}</p>}
                    </td>
                    <td style={{ padding: 10, borderTop: "1px solid #e2e8f0" }}>
                      {nombreCliente(proyecto.idCliente)}
                    </td>
                    <td style={{ padding: 10, borderTop: "1px solid #e2e8f0" }}>
                      {proyecto.fechaInicio || "—"}
                    </td>
                    <td style={{ padding: 10, borderTop: "1px solid #e2e8f0" }}>
                      {proyecto.fechaFin || "—"}
                    </td>
                    <td style={{ padding: 10, borderTop: "1px solid #e2e8f0" }}>
                      {proyecto.estado || "Pendiente"}
                    </td>
                    <td style={{ padding: 10, borderTop: "1px solid #e2e8f0" }}>
                      <button type="button" onClick={() => manejarEditar(proyecto)}>
                        Editar
                      </button>{" "}
                      <button type="button" onClick={() => manejarEliminar(proyecto)}>
                        Eliminar
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </section>
    </main>
  );
}

export default PaginaProyectos;
