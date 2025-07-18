import { create } from "zustand";
import { Filme } from "../interfaces/Filme";

interface FilmeStore {
  pagina: number;
  tamanho: number;
  titulo: string;
  mensagem: string;
  filmeSelecionado: Filme | null;
  setPagina: (pagina: number) => void;
  setTamanho: (tamanho: number) => void;
  setTitulo: (titulo: string) => void;
  setMensagem: (mensagem: string) => void;
  setFilmeSelecionado: (filme: Filme | null) => void;
}

export const useFilmeStore = create<FilmeStore>((set) => ({
  pagina: 0,
  tamanho: 6,
  titulo: "",
  mensagem: "",
  filmeSelecionado: null,
  setPagina: (pagina) => set({ pagina }),
  setTamanho: (tamanho) => set({ tamanho }),
  setTitulo: (titulo) => set({ titulo }),
  setMensagem: (mensagem) => set({ mensagem }),
  setFilmeSelecionado: (filme) => set({ filmeSelecionado: filme }),
}));
