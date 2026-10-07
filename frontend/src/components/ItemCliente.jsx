// Componente reutilizable que representa un cliente individual.
// Permite consultar, editar y eliminar los datos del cliente.

function ItemCliente({ cliente, onEditar, onEliminar }) {
  return (
    <article className="item-cliente">
      <div className="datos-cliente">
        <p>
       <strong>ID:</strong> {cliente.id}
        </p>

        <p>
          <strong>Nombre:</strong> {cliente.nombre}
        </p>

        <p>
          <strong>Correo:</strong> {cliente.email}
        </p>

        <p>
          <strong>Teléfono:</strong> {cliente.telefono}
        </p>

        <p>
          <strong>Empresa:</strong> {cliente.empresa}
        </p>
      </div>

      <div className="acciones-cliente">
        <button
          type="button"
          onClick={() => onEditar(cliente)}
        >
          Editar
        </button>

        <button
          type="button"
          onClick={() => {
  console.log("ID a eliminar:", cliente.id);
  onEliminar(cliente.id);
}}
        >
          Eliminar
        </button>
      </div>
    </article>
  );
}

export default ItemCliente;
