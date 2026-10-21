import "./App.css";
import { useState, useEffect } from "react";
import Encabezado from "./components/Encabezado";
import PaginaClientes from "./pages/PaginaClientes";
import PaginaProyectos from "./pages/PaginaProyectos";

function App() {
  const [seccion, setSeccion] = useState(
    window.location.hash.replace("#", "") || "clientes"
  );

  useEffect(() => {
    const cambiarSeccion = () => {
      setSeccion(
        window.location.hash.replace("#", "") || "clientes"
      );
    };

    window.addEventListener("hashchange", cambiarSeccion);

    return () => {
      window.removeEventListener("hashchange", cambiarSeccion);
    };
  }, []);

  return (
    <>
      <Encabezado />

      {seccion === "proyectos" ? (
        <PaginaProyectos />
      ) : (
        <PaginaClientes />
      )}
    </>
  );
}

export default App;
