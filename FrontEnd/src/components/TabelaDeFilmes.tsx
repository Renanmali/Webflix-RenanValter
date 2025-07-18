import dayjs from "dayjs";
import { Filme } from "../interfaces/Filme";
import { Link } from "react-router-dom";
import { useFilmeStore } from "../store/FilmeStore";
import useRecuperarFilmesComPaginacao from "../hooks/useRecuperarFilmesComPaginacao";
import useRemoverFilmePorId from "../hooks/useRemoverFilmePorId";

const TabelaDeFilmes = () => {
  const pagina = useFilmeStore((s) => s.pagina);
  const tamanho = useFilmeStore((s) => s.tamanho);
  const titulo = useFilmeStore((s) => s.titulo);

  const definirPagina = useFilmeStore((s) => s.definirPagina);
  const setMensagem = useFilmeStore((s) => s.setMensagem);

  const {
    data: resultadoPaginado,
    isPending: carregandoFilmes,
    error: errorFilmes,
  } = useRecuperarFilmesComPaginacao({
    pagina: pagina.toString(),
    tamanho: tamanho.toString(),
    titulo,
  });

  const { mutate: removerFilme, error: errorRemocaoFilme } = useRemoverFilmePorId();

  const tratarRemocao = (id: number) => {
    removerFilme(id);
    definirPagina(0);
  };

  if (carregandoFilmes) return <p className="fw-bold">Carregando filmes...</p>;
  if (errorFilmes) throw errorFilmes;
  if (errorRemocaoFilme) throw errorRemocaoFilme;

  const filmes: Filme[] = resultadoPaginado.itens;

  return (
    <div className="table-responsive">
      <table className="table table-bordered table-sm table-hover table-striped">
        <thead>
          <tr>
            <th className="text-center align-middle">Id</th>
            <th className="text-center align-middle">Imagem</th>
            <th className="text-center align-middle">Gênero</th>
            <th className="text-center align-middle">Título</th>
            <th className="text-center align-middle">Ano de Lançamento</th>
            <th className="text-center align-middle">Ação</th>
          </tr>
        </thead>
        <tbody>
          {filmes.map((filme) => (
            <tr key={filme.id}>
              <td className="text-center align-middle">{filme.id}</td>
              <td className="text-center align-middle">
                <img src={filme.imagem} alt="imagem do filme" style={{ width: "40px" }} />
              </td>
              <td className="text-center align-middle">{filme.genero.nome}</td>
              <td className="align-middle ps-3">
                <Link onClick={() => setMensagem("")} style={{ textDecoration: "none" }} to={"/filmes/" + filme.id}>
                  {filme.titulo}
                </Link>
              </td>
              <td className="text-center align-middle">{dayjs(filme.anoLancamento).format("YYYY")}</td>
              <td className="text-center align-middle">
                <button onClick={() => tratarRemocao(filme.id!)} className="btn btn-danger btn-sm" type="button">
                  Remover
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};
export default TabelaDeFilmes;
