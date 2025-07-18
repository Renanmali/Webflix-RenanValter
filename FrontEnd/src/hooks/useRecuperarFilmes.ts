import { Favorito } from "../interfaces/Favorito";
import { useQuery } from "@tanstack/react-query";
import { api } from "./useAPI";

export function useRecuperarFavoritos(clienteId: number) {
  return useQuery<Favorito[]>({
    queryKey: ["favoritos", clienteId],
    queryFn: async () => {
      const { data } = await api.get(`/favoritos/cliente/${clienteId}`);
      return data;
    },
  });
}
