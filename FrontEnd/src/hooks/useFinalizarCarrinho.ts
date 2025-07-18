import { Locacao } from "../interfaces/Locacao";
import { useMutation } from "@tanstack/react-query";
import { api } from "./useAPI";

export function useFinalizarCarrinho() {
  return useMutation({
    mutationFn: async (carrinhoId: number) => {
      const { data } = await api.post<Locacao[]>(`/carrinhos/${carrinhoId}/finalizar`);
      return data;
    },
  });
}
