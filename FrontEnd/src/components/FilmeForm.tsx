import { useEffect } from "react";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import slugify from "slugify";
import databaseAdd from "../assets/skin/database_add.png";
import databaseEdit from "../assets/skin/database_edit.png";
import databaseCancel from "../assets/skin/multiply.png";
import useAlterarFilme from "../hooks/useAlterarFilme";
import useCadastrarFilme from "../hooks/useCadastrarFilme";
import { useFilmeStore } from "../store/FilmeStore";
import z from "zod";
import { zodResolver } from "@hookform/resolvers/zod";

const schema = z.object({
  titulo: z.string().nonempty("O título deve ser informado.").min(2),
  sinopse: z.string().nonempty("A sinopse deve ser informada.").min(10),
  diretor: z.string().nonempty("O diretor deve ser informado.").min(3),
  anoLancamento: z.coerce.number().min(1900),
  imagem: z.string().nonempty("A imagem deve ser informada."),
});

type FilmeForm = z.infer<typeof schema>;

const FilmeForm = () => {
  const setMensagem = useFilmeStore((s) => s.setMensagem);
  const filmeSelecionado = useFilmeStore((s) => s.filmeSelecionado);
  const navigate = useNavigate();

  const { mutate: cadastrarFilme, error: errorCadastrarFilme } = useCadastrarFilme();
  const { mutate: alterarFilme, error: errorAlterarFilme } = useAlterarFilme();

  const { register, handleSubmit, setValue, reset, formState: { errors } } = useForm<FilmeForm>({
    defaultValues: {
      titulo: "",
      sinopse: "",
      diretor: "",
      anoLancamento: undefined,
      imagem: "",
    },
    resolver: zodResolver(schema),
  });

  const setValoresIniciais = () => {
    if (filmeSelecionado?.id) {
      setValue("titulo", filmeSelecionado.titulo);
      setValue("sinopse", filmeSelecionado.sinopse);
      setValue("diretor", filmeSelecionado.diretor);
      setValue("anoLancamento", filmeSelecionado.anoLancamento);
      setValue("imagem", filmeSelecionado.imagem);
    } else {
      reset();
    }
  };

  useEffect(() => {
    setValoresIniciais();
  }, [filmeSelecionado]);

  const submit = ({ titulo, sinopse, diretor, anoLancamento, imagem }: FilmeForm) => {
    const filme = {
      titulo,
      slug: slugify(titulo, { lower: true, strict: true }),
      sinopse,
      diretor,
      anoLancamento,
      imagem,
    };

    if (filmeSelecionado?.id) {
      alterarFilme({ ...filme, id: filmeSelecionado.id }, {
        onSuccess: (filmeAlterado) => {
          setMensagem("Filme alterado com sucesso!");
          navigate("/filmes/" + filmeAlterado.id);
        },
      });
    } else {
      cadastrarFilme(filme, {
        onSuccess: (filmeCadastrado) => {
          setMensagem("Filme cadastrado com sucesso!");
          navigate("/filmes/" + filmeCadastrado.id);
        },
      });
    }
  };

  if (errorCadastrarFilme) throw errorCadastrarFilme;
  if (errorAlterarFilme) throw errorAlterarFilme;

  return (
    <form onSubmit={handleSubmit(submit)} autoComplete="off">
      <div className="mb-2">
        <label htmlFor="titulo">Título</label>
        <input {...register("titulo")} type="text" id="titulo" className={`form-control ${errors.titulo ? "is-invalid" : ""}`} />
        <div className="invalid-feedback">{errors.titulo?.message}</div>
      </div>
      <div className="mb-2">
        <label htmlFor="sinopse">Sinopse</label>
        <textarea {...register("sinopse")} id="sinopse" className={`form-control ${errors.sinopse ? "is-invalid" : ""}`} />
        <div className="invalid-feedback">{errors.sinopse?.message}</div>
      </div>
      <div className="mb-2">
        <label htmlFor="diretor">Diretor</label>
        <input {...register("diretor")} type="text" id="diretor" className={`form-control ${errors.diretor ? "is-invalid" : ""}`} />
        <div className="invalid-feedback">{errors.diretor?.message}</div>
      </div>
      <div className="mb-2">
        <label htmlFor="anoLancamento">Ano de Lançamento</label>
        <input {...register("anoLancamento", { valueAsNumber: true })} type="number" id="anoLancamento" className={`form-control ${errors.anoLancamento ? "is-invalid" : ""}`} />
        <div className="invalid-feedback">{errors.anoLancamento?.message}</div>
      </div>
      <div className="mb-2">
        <label htmlFor="imagem">Imagem</label>
        <input {...register("imagem")} type="text" id="imagem" className={`form-control ${errors.imagem ? "is-invalid" : ""}`} />
        <div className="invalid-feedback">{errors.imagem?.message}</div>
      </div>
      <div className="mt-3">
        <button type="submit" className="btn btn-primary me-2">
          {filmeSelecionado?.id ? <><img src={databaseEdit} className="me-1" /> Alterar</> : <><img src={databaseAdd} className="me-1" /> Cadastrar</>}
        </button>
        <button onClick={() => setValoresIniciais()} type="button" className="btn btn-secondary">
          <img src={databaseCancel} className="me-1" /> Cancelar
        </button>
      </div>
    </form>
  );
};
export default FilmeForm;
