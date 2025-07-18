import { Filme } from "../interfaces/Filme";
import { useQuery } from "@tanstack/react-query";
import { api } from "./useAPI";

export function useRecuperarFilmesPorSlugGenero(slugGenero: string) {
  return useQuery<Filme[]>({
    queryKey: ["filmes", slugGenero],
    queryFn: async () => {
      const { data } = await api.get(`/catalogo/genero/${slugGenero}`);
      return data;
    },
  });
}