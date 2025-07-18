import { Filme } from "../interfaces/Filme";
import { CarrinhoItem } from "../interfaces/Carrinho";

interface Props {
  filme: Filme;
  filmeNoCarrinho: CarrinhoItem | null;
  adicionarFilme: (filme: Filme) => void;
  removerFilme: (filme: Filme) => void;
}

const Card = ({ filme, adicionarFilme, removerFilme, filmeNoCarrinho }: Props) => {
  return (
    <div className="card h-100 border-0">
      <img src={filme.imagem} className="card-img-top" alt={filme.titulo} />
      <div className="card-body">
        <h5 className="card-title">{filme.titulo}</h5>
        <p className="card-text">{filme.sinopse}</p>
        <p className="card-text fw-bold" style={{ color: "rgb(220,60,60)" }}>
          {filme.anoLancamento}
        </p>
      </div>
      <div className="card-footer p-0 mb-4">
        {filmeNoCarrinho ? (
          <div className="btn-group w-100">
            <button onClick={() => removerFilme(filme)} type="button" className="btn btn-secondary btn-sm">-</button>
            <button type="button" className="btn btn-secondary btn-sm">{filmeNoCarrinho?.quantidade}</button>
            <button onClick={() => adicionarFilme(filme)} type="button" className="btn btn-secondary btn-sm">+</button>
          </div>
        ) : (
          <button onClick={() => adicionarFilme(filme)} type="button" className="btn btn-success btn-sm w-100">Alugar</button>
        )}
      </div>
    </div>
  );
};
export default Card;