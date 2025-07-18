import { ReactNode } from "react";
import { useFilmeStore } from "../store/FilmeStore";
import useRecuperarFilmesComPaginacao from "../hooks/useRecuperarFilmesComPaginacao";

const Paginacao = () => {
  const pagina = useFilmeStore((s) => s.pagina);
  const tamanho = useFilmeStore((s) => s.tamanho);
  const titulo = useFilmeStore((s) => s.titulo);

  const setPagina = useFilmeStore((s) => s.definirPagina);

  const {
    data: resultadoPaginado,
    isPending: carregandoFilmes,
    error: errorFilmes,
  } = useRecuperarFilmesComPaginacao({
    pagina: pagina.toString(),
    tamanho: tamanho.toString(),
    titulo,
  });

  const tratarPaginacao = (pagina: number) => {
    setPagina(pagina);
  };

  if (carregandoFilmes) return <p className="fw-bold">Carregando filmes...</p>;
  if (errorFilmes) throw errorFilmes;

  const totalDePaginas: number = resultadoPaginado.totalDePaginas;

  const arrayDePaginas: ReactNode[] = [];

  for (let i = 0; i < totalDePaginas; i++) {
    arrayDePaginas.push(
      <li key={i} className={pagina === i ? "page-item active" : "page-item"}>
        <button
          onClick={() => tratarPaginacao(i)}
          className="page-link"
          aria-current="page"
        >
          {i + 1}
        </button>
      </li>
    );
  }

  if (totalDePaginas < 2) return null;

  return (
    <nav aria-label="paginacao">
      <ul className="pagination">
        <li className={pagina === 0 ? "page-item disabled" : "page-item"}>
          <button onClick={() => tratarPaginacao(pagina - 1)} className="page-link">
            Anterior
          </button>
        </li>
        {arrayDePaginas}
        <li className={pagina === totalDePaginas - 1 ? "page-item disabled" : "page-item"}>
          <button onClick={() => tratarPaginacao(pagina + 1)} className="page-link">
            Próxima
          </button>
        </li>
      </ul>
    </nav>
  );
};
export default Paginacao;
