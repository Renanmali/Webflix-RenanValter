import { useQuery } from "@tanstack/react-query";
import Filme from "../interfaces/Filme";
import isErrorResponse from "../util/isErrorResponse";

const useRecuperarFilmePorId = (id: number, removido: boolean) => {
  
  const recuperarFilmePorId = async (id: number): Promise<Filme> => {
    const response = await fetch("http://localhost:8080/filmes/" + id);
    if (!response.ok) {
      const error = (await response.json()) as unknown;
      if (isErrorResponse(error)) {
        throw error;
      }
      throw new Error(
        "Ocorreu um erro ao recuperar o filme com id = " +
          id +
          ". Status code = " +
          response.status
      );
    }
    return await response.json();
  };

  return useQuery({
    queryKey: ["filme", id],
    queryFn: () => recuperarFilmePorId(id),
    staleTime: 10_000,
    enabled: !removido
  });
};
export default useRecuperarFilmePorId;
