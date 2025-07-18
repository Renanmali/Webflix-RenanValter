import { Filme } from "../interfaces/Filme";
import { ResultadoPaginado } from "../interfaces/ResultadoPaginado";
import { useQuery } from "@tanstack/react-query";
import { api } from "./useAPI";

export function useRecuperarFilmesPorSlugGeneroComPaginacao(slugGenero: string, pagina: number, tamanho: number) {
  return useQuery<ResultadoPaginado<Filme>>({
    queryKey: ["filmes", slugGenero, pagina, tamanho],
    queryFn: async () => {
      const { data } = await api.get(`/catalogo/genero/${slugGenero}/paginado`, {
        params: { pagina, tamanho },
      });
      return data;
    },
  });
}
