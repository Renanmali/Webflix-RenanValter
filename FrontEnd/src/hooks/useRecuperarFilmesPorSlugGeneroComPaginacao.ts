import { Filme } from "../interfaces/Filme";
import {ResultadoPaginado} from "../interfaces/ResultadoPaginado";
import { useInfiniteQuery } from "@tanstack/react-query";
import { api } from "./useAPI";

interface QueryString {
  slugGenero: string;
  tamanho: string;
}

export function useRecuperarFilmesPorSlugGeneroComPaginacao(query: QueryString) {
  return useInfiniteQuery({
    queryKey: ["filmes", "genero", query],
    initialPageParam: 0,
    queryFn: async ({ pageParam = 0 }) => {
      const { data } = await api.get<ResultadoPaginado<Filme>>("/filmes/genero/paginacao", {
        params: { pagina: pageParam, tamanho: query.tamanho, slugGenero: query.slugGenero },
      });
      return data;
    },
    getNextPageParam: (lastPage) =>
      lastPage.paginaCorrente < lastPage.totalDePaginas - 1
        ? lastPage.paginaCorrente + 1
        : undefined,
  });
}
