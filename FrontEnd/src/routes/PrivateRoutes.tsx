import { Navigate, useLocation } from "react-router-dom";
import { useClienteStore } from "../store/ClienteStore";
import Layout from "./Layout";

const PrivateRoutes = () => {
  const cliente = useClienteStore((s) => s.cliente);
  const location = useLocation();

  if (cliente) {
    return <Layout />;
  } else {
    return <Navigate to="/login" state={{ destino: location.pathname }} />;
  }
};
export default PrivateRoutes;