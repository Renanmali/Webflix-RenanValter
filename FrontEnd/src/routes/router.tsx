import { createBrowserRouter } from "react-router-dom";
import CardsPorSlugGeneroPage from "../pages/CardsPorSlugGeneroPage";
import CarrinhoPage from "../pages/CarrinhoPage";
import ErrorPage from "../pages/ErrorPage";
import FavoritosPage from "../pages/FavoritosPage";
import HomePage from "../pages/HomePage";
import LoginPage from "../pages/LoginPage";
import RegistroPage from "../pages/RegistroPage";
import FilmePage from "../pages/FilmePage";
import FilmesComPaginacaoPage from "../pages/FilmesComPaginacaoPage";
import Layout from "./Layout";
import PrivateRoutes from "./PrivateRoutes";
import CadastrarFilmePage from "../pages/CadastrarFilmePage";

const router = createBrowserRouter([
  {
    path: "/",
    element: <Layout />, 
    errorElement: <ErrorPage />, 
    children: [
      {
        path: "",
        element: <HomePage />, 
        children: [
          { path: ":slugGenero?", element: <CardsPorSlugGeneroPage /> },
        ],
      },
      { path: "filmes", element: <FilmesComPaginacaoPage /> },
      { path: "cadastrar-filme", element: <CadastrarFilmePage /> },
      { path: "filmes/:id", element: <FilmePage /> },
      { path: "registro", element: <RegistroPage /> },
      { path: "login", element: <LoginPage /> },
    ],
  },
  {
    path: "/",
    element: <PrivateRoutes />,
    errorElement: <ErrorPage />,
    children: [
      { path: "carrinho", element: <CarrinhoPage /> },
      { path: "favoritos", element: <FavoritosPage /> },
    ],
  },
]);
export default router;
