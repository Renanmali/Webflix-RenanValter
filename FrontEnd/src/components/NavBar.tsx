import { NavLink } from "react-router-dom";
import locadora from "../assets/locadora.png";
import { useClienteStore } from "../store/ClienteStore";
import { Filme } from "../interfaces/Filme";
import { useFilmeStore } from "../store/FilmeStore";

const NavBar = () => {
  const cliente = useClienteStore((s) => s.cliente);
  const definirFilmeSelecionado = useFilmeStore((s) => s.definirFilmeSelecionado);

  return (
    <nav className="navbar navbar-expand-lg bg-light navbar-light">
      <div className="container">
        <NavLink className="navbar-brand" to="/">
          <img src={locadora} alt="logo da locadora" style={{ width: "50px" }} />
        </NavLink>
        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNav"
          aria-controls="navbarNav"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarNav">
          <ul className="navbar-nav">
            <li className="nav-item">
              <NavLink className="nav-link" aria-current="page" to="/">
                Home
              </NavLink>
            </li>
            <li className="nav-item">
              <NavLink className="nav-link" to="/favoritos">
                Favoritos
              </NavLink>
            </li>
            <li className="nav-item">
              <NavLink className="nav-link" to="/filmes">
                Filmes
              </NavLink>
            </li>
            <li className="nav-item">
              <NavLink onClick={() => definirFilmeSelecionado({} as Filme)} className="nav-link" to="/cadastrar-filme">
                Cadastrar Filme
              </NavLink>
            </li>
          </ul>
          <ul className="navbar-nav ms-auto">
            <li className="nav-item">
              <NavLink className="nav-link" to="/carrinho">
                Carrinho
              </NavLink>
            </li>
            <li className="nav-item">
              <NavLink className="nav-link" to="/login">
                {cliente ? "Sair" : "Entrar"}
              </NavLink>
            </li>
            {!cliente && (
              <li className="nav-item">
                <NavLink className="nav-link" to="/registro">
                  Registrar
                </NavLink>
              </li>
            )}
          </ul>
        </div>
      </div>
    </nav>
  );
};
export default NavBar;
