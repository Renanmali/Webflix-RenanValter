import { create } from "zustand";
import { Cliente } from "../interfaces/Cliente";

interface ClienteStore {
  cliente: Cliente | null;
  definirCliente: (cliente: Cliente | null) => void;
}

export const useClienteStore = create<ClienteStore>((set) => ({
  cliente: null,
  definirCliente: (cliente) => set({ cliente }),
}));
