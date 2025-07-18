import { FormEvent, useRef } from "react";
import { useFilmeStore } from "../store/FilmeStore";

const Pesquisa = () => {
  const setTitulo = useFilmeStore((s) => s.definirTitulo);
  const setPagina = useFilmeStore((s) => s.definirPagina);

  const tituloRef = useRef<HTMLInputElement>(null);

  const tratarTitulo = (titulo: string) => {
    setTitulo(titulo);
    setPagina(0);
  };

  return (
    <form
      onSubmit={(event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        tratarTitulo(tituloRef.current!.value);
      }}
      className="d-flex flex-row mb-3"
    >
      <input
        ref={tituloRef}
        type="text"
        className="form-control form-control-sm me-3"
        placeholder="Informe o título do filme..."
      />
      <button type="submit" className="btn btn-primary btn-sm px-4">
        Pesquisar
      </button>
    </form>
  );
};
export default Pesquisa;
