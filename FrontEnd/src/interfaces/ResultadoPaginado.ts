import { Genero } from "../interfaces/Genero";

export function isGeneroValido(genero: Genero | undefined | null): boolean {
  return !!genero?.nome?.trim() && !!genero?.slug?.trim();
}
