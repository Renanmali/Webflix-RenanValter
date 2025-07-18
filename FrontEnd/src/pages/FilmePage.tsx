import { useNavigate, useParams } from "react-router-dom";
import useRecuperarFilmePorId from "../hooks/useRecuperarFilmePorId";
import dayjs from "dayjs";
import useRemoverFilmePorId from "../hooks/useRemoverFilmePorId";
import { useState } from "react";
import { useFilmeStore } from "../store/FilmeStore";

const FilmePage = () => {
  const [removido, setRemovido] = useState(false);
  const mensagem = useFilmeStore((s) => s.mensagem);
  const setMensagem = useFilmeStore((s) => s.setMensagem);
  const definirFilmeSelecionado = useFilmeStore((s) => s.definirFilmeSelecionado);

  const { id } = useParams();
  const navigate = useNavigate();

  const { data: filme, isPending, error } = useRecuperarFilmePorId(+id!, removido);
  const { mutate: removerFilme } = useRemoverFilmePorId();

  const tratarRemocao = (id: number) => {
    setMensagem("Filme removido com sucesso!");
    removerFilme(id);
    setRemovido(true);
  };

  if (isPending) return <p className="fw-bold">Carregando filme...</p>;
  if (error) throw error;

  return (
    <>
      <div className="mb-4">
        <h5>Detalhes do Filme</h5>
        <hr className="mt-1" />
      </div>

      {mensagem && <div className="alert alert-primary">{mensagem}</div>}

      <div className="row">
        <div className="col-lg-3 col-md-4">
          <img src={"/" + filme.imagem} className="d-block d-md-none mb-3" style={{ width: "170px" }} />
          <img src={"/" + filme.imagem} className="d-none d-md-block" style={{ width: "210px" }} />
        </div>
        <div className="col-lg-9 col-md-8">
          <div className="row">
            <div className="col-xl-2 col-lg-3 col-4 fw-bold mb-1">Gênero</div>
            <div className="col-xl-10 col-lg-9 col-8">{filme.genero.nome}</div>
            <div className="col-xl-2 col-lg-3 col-4 fw-bold mb-1">Título</div>
            <div className="col-xl-10 col-lg-9 col-8">{filme.titulo} ({filme.sinopse})</div>
            <div className="col-xl-2 col-lg-3 col-4 fw-bold mb-1">Ano Lançamento</div>
            <div className="col-xl-10 col-lg-9 col-8">{dayjs(filme.anoLancamento).format("YYYY")}</div>
          </div>
        </div>
        <div className="col-lg-3 col-md-4 col-6 mt-3">
          <button disabled={removido} onClick={() => { definirFilmeSelecionado(filme); navigate("/cadastrar-filme"); }} className="btn btn-primary btn-sm me-3 w-100" type="button">
            Editar
          </button>
        </div>
        <div className="col-lg-3 col-md-4 col-6 mt-3">
          <button disabled={removido} onClick={() => tratarRemocao(filme.id!)} className="btn btn-danger btn-sm w-100" type="button">
            Remover
          </button>
        </div>
      </div>
    </>
  );
};
export default FilmePage;