import { Carrinho } from "../interfaces/Carrinho";
import { useQuery } from "@tanstack/react-query";
import { api } from "./useAPI";

export function useVisualizarCarrinho(clienteId: number) {
  return useQuery<Carrinho>({
    queryKey: ["carrinho", clienteId],
    queryFn: async () => {
      const { data } = await api.get(`/carrinhos/cliente/${clienteId}`);
      return data;
    },
  });
}
