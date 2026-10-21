const API_URL = "http://localhost:8081/api/proyectos";

async function comprobarRespuesta(respuesta, mensaje) {
  if (!respuesta.ok) {
    const texto = await respuesta.text();
    throw new Error(texto || `${mensaje} Estado HTTP: ${respuesta.status}`);
  }

  if (respuesta.status === 204) {
    return null;
  }

  return respuesta.json();
}

export async function obtenerProyectos() {
  const respuesta = await fetch(API_URL);
  return comprobarRespuesta(respuesta, "No se pudieron consultar los proyectos.");
}

export async function crearProyecto(proyecto) {
  const respuesta = await fetch(API_URL, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(proyecto),
  });

  return comprobarRespuesta(respuesta, "No se pudo registrar el proyecto.");
}

export async function actualizarProyecto(id, proyecto) {
  const respuesta = await fetch(`${API_URL}/${id}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(proyecto),
  });

  return comprobarRespuesta(respuesta, "No se pudo actualizar el proyecto.");
}

export async function eliminarProyecto(id) {
  const respuesta = await fetch(`${API_URL}/${id}`, {
    method: "DELETE",
  });

  return comprobarRespuesta(respuesta, "No se pudo eliminar el proyecto.");
}
