// Componente encargado de mostrar la lista de clientes registrados.
// Recibe los clientes y las acciones de editar y eliminar mediante props.

import ItemCliente from "./ItemCliente";

function ListaClientes({ clientes, onEditar, onEliminar }) {
  return (
    <section className="lista-clientes">
      <h2>Clientes registrados</h2>

      {clientes.length === 0 ? (
        <p className="sin-clientes">
          No hay clientes registrados.
        </p>
      ) : (
        <div className="contenedor-clientes">
          {clientes.map((cliente) => (
            <ItemCliente
              key={cliente.id}
              cliente={cliente}
              onEditar={onEditar}
              onEliminar={onEliminar}
            />
          ))}
        </div>
      )}
    </section>
  );
}

export default ListaClientes;