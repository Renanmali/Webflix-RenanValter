import { useMutation } from "@tanstack/react-query";
import queryClient from "../main";
import isErrorResponse from "../util/isErrorResponse";

const removerFilmePorId = async (id: number) => {
  const response = await fetch("http://localhost:8080/filmes/" + id, {
    method: "DELETE",
  });
  if (!response.ok) {
    const error = (await response.json()) as unknown;
    if (isErrorResponse(error)) {
      throw error;
    }
    throw new Error(
      "Ocorreu um erro ao remover o filme com id = " +
        id +
        ". Status code = " +
        response.status
    );
  }
  // return await response.json(); não retornar nada pois o back-end retorna void.
};

const useRemoverFilmePorId = () => {
  return useMutation({
    mutationFn: (id: number) => removerFilmePorId(id),
    onSuccess: (_, id) => {
      queryClient.invalidateQueries({
        queryKey: ["filmes"],
      });
      queryClient.invalidateQueries({
        queryKey: ["filme", id],
      });
    },
  });
};
export default useRemoverFilmePorId;
