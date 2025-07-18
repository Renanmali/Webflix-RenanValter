import { useQuery } from "@tanstack/react-query";
import { api } from "./useAPI";
import { Genero } from "../interfaces/Genero";
import ResultadoPaginado from "../interfaces/ResultadoPaginado";

const useRecuperarGeneros = () => {
  return useQuery({
    queryKey: ["generos"],
    queryFn: async () => {
      const { data } = await api.get<ResultadoPaginado<Genero>>("/generos", {
        params: { pagina: 0, tamanho: 100, nome: "" },
      });
      return data.itens;
    },
  });
};

export default useRecuperarGeneros;
