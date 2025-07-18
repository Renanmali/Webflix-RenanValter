import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import Card from "../components/Card";
import { useRecuperarFilmesPorSlugGeneroComPaginacao } from "../hooks/useRecuperarFilmesPorSlugGeneroComPaginacao";
import { Filme }from "../interfaces/Filme";
import { useFilmeStore } from "../store/FilmeStore";
import CardsPlaceholderPage from "./CardsPlaceholderPage";
import InfiniteScroll from "react-infinite-scroll-component";

export interface FilmeCarrinho {
  idFilme: number;
  quantidade: number;
}

const CardsPorSlugGeneroPage = () => {
  const tamanho = useFilmeStore((s) => s.tamanho);

  const [carrinho, setCarrinho] = useState(() => {
    const itensDeCarrinho = localStorage.getItem("carrinho");
    return itensDeCarrinho ? JSON.parse(itensDeCarrinho) : [];
  });

  useEffect(() => {
    localStorage.setItem("carrinho", JSON.stringify(carrinho));
  }, [carrinho]);

  const adicionarFilme = (filme: Filme) => {
    setCarrinho((prevCarrinho: FilmeCarrinho[]) => {
      const existe = prevCarrinho.find((item) => item.idFilme === filme.id);
      if (existe) {
        return prevCarrinho.map((item) =>
          item.idFilme === filme.id
            ? { idFilme: item.idFilme, quantidade: item.quantidade + 1 }
            : item
        );
      } else {
        return [...prevCarrinho, { idFilme: filme.id, quantidade: 1 }];
      }
    });
  };

  const subtrairFilme = (filme: Filme) => {
    setCarrinho((prevCarrinho: FilmeCarrinho[]) => {
      const existe = prevCarrinho.find((item) => item.idFilme === filme.id);
      if (existe) {
        const novoCarrinho = prevCarrinho.map((item) =>
          item.idFilme === filme.id
            ? { idFilme: item.idFilme, quantidade: item.quantidade - 1 }
            : item
        );
        return novoCarrinho.filter((item) => item.quantidade > 0);
      } else {
        throw new Error("Erro ao subtrair 1 de filme no carrinho.");
      }
    });
  };

  const { slugGenero } = useParams();
  const {
    data,
    isPending: carregandoFilmes,
    error: errorFilmes,
    hasNextPage,
    fetchNextPage,
  } = useRecuperarFilmesPorSlugGeneroComPaginacao({
    tamanho: tamanho.toString(),
    slugGenero: slugGenero ? slugGenero : "",
  });

  if (carregandoFilmes) return <CardsPlaceholderPage />;
  if (errorFilmes) throw errorFilmes;

  const filmesNoCarrinho: (FilmeCarrinho | null)[] = [];
  data.pages.forEach((page) => {
    page.itens.forEach((filme) => {
      const prodCarrinho = carrinho.find((item) => item.idFilme === filme.id);
      filmesNoCarrinho.push(prodCarrinho ? prodCarrinho : null);
    });
  });

  return (
    <InfiniteScroll
      style={{ overflowX: "hidden" }}
      dataLength={data.pages.reduce((total, page) => total + page.totalDeItens, 0)}
      hasMore={hasNextPage}
      next={() => fetchNextPage()}
      loader={<h6>Carregando...</h6>}
    >
      <h5>{slugGenero ? slugGenero.charAt(0).toUpperCase() + slugGenero.slice(1) : "Filmes"}</h5>
      <div className="row">
        {data.pages.map((page, pagina) =>
          page.itens.map((filme, index) => (
            <div key={filme.id} className="col-lg-2 col-md-3 col-sm-4 col-6">
              <Card
                filme={filme}
                filmeNoCarrinho={filmesNoCarrinho[pagina * tamanho + index]}
                adicionarProduto={adicionarFilme}
                subtrairProduto={subtrairFilme}
              />
            </div>
          ))
        )}
      </div>
    </InfiniteScroll>
  );
};
export default CardsPorSlugGeneroPage;