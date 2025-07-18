import { Cliente } from "../interfaces/Cliente";
import { useMutation } from "@tanstack/react-query";
import { api } from "./useAPI";

export function useRegistrarCliente() {
  return useMutation({
    mutationFn: async (cliente: Omit<Cliente, "id"> & { confirmacaoSenha: string }) => {
      const { data } = await api.post<Cliente>("/clientes", cliente);
      return data;
    },
  });
}
