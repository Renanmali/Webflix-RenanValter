import Filme from "./Filme";

interface Copia {
  id: number;
  filme: Filme;
  status: string;
}

interface CarrinhoItem {
  id: number;
  copia: Copia;
}

interface Carrinho {
  id: number;
  itens: CarrinhoItem[];
  status: string;
}
export default Carrinho;
