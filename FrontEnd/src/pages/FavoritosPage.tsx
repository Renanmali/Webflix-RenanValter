import { useClienteStore } from "../store/ClienteStore";
import { useRecuperarFavoritos }from "../hooks/useRecuperarFavoritos";

const FavoritosPage = () => {
  const cliente = useClienteStore((s) => s.cliente);
  const { data, isPending, error } = useRecuperarFavoritos(cliente?.id || 0);

  if (isPending) return <p>Carregando...</p>;
  if (error) throw error;
  if (!data) return <p>Nenhum favorito.</p>;

  return (
    <div>
      <h5>Favoritos</h5>
      <div className="row">
        {data.map((fav) => (
          <div key={fav.id} className="col-lg-3 col-md-4 col-6 mb-3">
            <div className="card h-100">
              <img src={"/" + fav.filme.imagem} className="card-img-top" alt={fav.filme.titulo} />
              <div className="card-body">
                <h6 className="card-title">{fav.filme.titulo}</h6>
                <a href="#">
                  <img src="/heart-fill.svg" alt="remover" width={20} />
                </a>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};
export default FavoritosPage;