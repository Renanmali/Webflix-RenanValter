import { useClienteStore } from "../store/ClienteStore";
import { useVisualizarCarrinho }from "../hooks/useVisualizarCarrinho";
import { useFinalizarCarrinho } from "../hooks/useFinalizarCarrinho";

const CarrinhoPage = () => {
  const cliente = useClienteStore((s) => s.cliente);
  const { data: carrinho, isPending, error } = useVisualizarCarrinho(cliente?.id || 0);
  const { mutate: finalizar } = useFinalizarCarrinho();

  if (isPending) return <p>Carregando...</p>;
  if (error) throw error;
  if (!carrinho) return <p>Nenhum carrinho.</p>;

  return (
    <div>
      <h5>Carrinho</h5>
      <ul>
        {carrinho.itens.map((item) => (
          <li key={item.id}>{item.copia.filme.titulo}</li>
        ))}
      </ul>
      <button className="btn btn-success" onClick={() => finalizar(carrinho.id)}>
        Finalizar
      </button>
    </div>
  );
};
export default CarrinhoPage;