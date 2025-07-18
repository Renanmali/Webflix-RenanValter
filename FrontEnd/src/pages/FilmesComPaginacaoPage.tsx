import Paginacao from "../components/Paginacao";
import Pesquisa from "../components/Pesquisa";
import TabelaDeFilmes from "../components/TabelaDeFilmes";

const FilmesComPaginacaoPage = () => {
  return (
    <>
      <h5>Lista de Filmes</h5>
      <hr className="mt-1" />
      <Pesquisa />
      <TabelaDeFilmes />
      <Paginacao />
    </>
  );
};
export default FilmesComPaginacaoPage;