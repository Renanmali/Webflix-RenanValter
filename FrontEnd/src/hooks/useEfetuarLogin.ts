import { Cliente } from "../interfaces/Cliente";
import { useMutation } from "@tanstack/react-query";
import { api } from "./useAPI";

export function useEfetuarLogin() {
  return useMutation({
    mutationFn: async (login: Omit<Cliente, "id">) => {
      const { data } = await api.post<Cliente>("/autenticacao/login", login);
      return data;
    },
  });
}
