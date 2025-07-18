import { useMutation } from "@tanstack/react-query";
import Filme from "../interfaces/Filme";
import queryClient from "../main";
import isErrorResponse from "../util/isErrorResponse";

const alterarFilme = async (filme: Filme) => {
  const response = await fetch("http://localhost:8080/filmes", {
    method: "PUT",
    headers: {
      // tipo do conteúdo que o back-end espera receber
      'Content-Type': 'application/json', 
    },
    body: JSON.stringify(filme)
  });
    if (!response.ok) {
      const error = (await response.json()) as unknown;
      if (isErrorResponse(error)) {
        throw error;
      }
      throw new Error(
        "Ocorreu um erro ao alterar um filme. Status code = " +
          response.status
      );
    }
  return await response.json(); 
};

const useAlterarFilme = () => {
  return useMutation({
    mutationFn: (filme: Filme) => alterarFilme(filme),
    onSuccess: (filmeAlterado: Filme) => {
      queryClient.invalidateQueries({
        queryKey: ["filmes"]
      })
      queryClient.invalidateQueries({
        queryKey: ["filme", filmeAlterado.id]
      })
    }
  });
}
export default useAlterarFilme;
