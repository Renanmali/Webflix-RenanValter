import { useParams } from "react-router-dom";
import CardPlaceholder from "../components/CardPlaceholder";

const CardsPlaceholderPage = () => {
  const { slugGenero } = useParams();

  return (
    <>
      <h5>
        {slugGenero
          ? slugGenero.charAt(0).toUpperCase() + slugGenero.slice(1)
          : "Filmes"}
      </h5>
      <div className="row">
        {Array.from({ length: 12 }).map((_, index) => (
          <div key={index} className="col-lg-2 col-md-3 col-sm-4 col-6">
            <CardPlaceholder />
          </div>
        ))}
      </div>
    </>
  );
};
export default CardsPlaceholderPage;