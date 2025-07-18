import { Genero } from "./Genero";

export interface Filme {
  id: number;
  titulo: string;
  slug: string;
  imagem: string;
  sinopse: string;
  diretor: string;
  anoLancamento: number;
  genero: Genero;
}
