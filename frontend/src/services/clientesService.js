// Servicio encargado de comunicarse con el API REST
// del módulo de clientes desarrollado en Spring Boot.

const API_URL = "http://localhost:8081/api/clientes";

// Obtiene todos los clientes registrados.
export async function obtenerClientes() {
  const respuesta = await fetch(API_URL);

  if (!respuesta.ok) {
    throw new Error("No se pudieron obtener los clientes.");
  }

  return await respuesta.json();
}

// Registra un nuevo cliente.
export async function crearCliente(cliente) {
  const respuesta = await fetch(API_URL, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(cliente),
  });

  if (!respuesta.ok) {
    throw new Error("No se pudo registrar el cliente.");
  }

  return await respuesta.json();
}

// Actualiza un cliente existente.
export async function actualizarCliente(id, cliente) {
  const respuesta = await fetch(`${API_URL}/${id}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(cliente),
  });

  if (!respuesta.ok) {
    throw new Error("No se pudo actualizar el cliente.");
  }

  return await respuesta.json();
}

// Elimina un cliente existente.
export async function eliminarCliente(id) {
  console.log("ID enviado para eliminar:", id);

  const respuesta = await fetch(`${API_URL}/${id}`, {
    method: "DELETE",
  });

  console.log("Estado DELETE:", respuesta.status);
  console.log("Respuesta DELETE:", respuesta);

  if (!respuesta.ok) {
  const textoError = await respuesta.text();

  console.error(
    "Error devuelto por Spring Boot:",
    textoError
  );

  throw new Error(
    textoError || `No se pudo eliminar el cliente. Estado HTTP: ${respuesta.status}`
  );
}
  return true;
}