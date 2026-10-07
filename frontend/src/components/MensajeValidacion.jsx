// Componente reutilizable para mostrar mensajes de éxito,
// advertencia o error al usuario.

function MensajeValidacion({ tipo = "error", mensaje }) {
  if (!mensaje) {
    return null;
  }

  return (
    <div className={`mensaje-validacion ${tipo}`} role="alert">
      {mensaje}
    </div>
  );
}

export default MensajeValidacion;
