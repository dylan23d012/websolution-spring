// Componente reutilizable encargado de mostrar el encabezado
// y la navegación principal del sistema Web Solution.

function Encabezado() {
  return (
    <header className="encabezado">
      <div className="logo">
        WEB SOLUTION
      </div>

      <nav className="navegacion">
        <a href="#">Inicio</a>
        <a href="#">Clientes</a>
        <a href="#">Proyectos</a>
        <a href="#">Servicios</a>
        <a href="#">Tareas</a>
        <a href="#">Reportes</a>
      </nav>
    </header>
  );
}

export default Encabezado;
