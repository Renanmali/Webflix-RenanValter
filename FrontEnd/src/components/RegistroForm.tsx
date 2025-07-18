import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import z from "zod";
import { useRegistrarCliente } from "../hooks/useRegistrarCliente";

const schema = z
  .object({
    conta: z.string().nonempty("A conta deve ser informada."),
    senha: z.string().min(4, "A senha deve ter pelo menos 4 caracteres."),
    confirmacaoSenha: z.string(),
  })
  .refine((data) => data.senha === data.confirmacaoSenha, {
    message: "As senhas não conferem.",
    path: ["confirmacaoSenha"],
  });

type FormRegistro = z.infer<typeof schema>;

const RegistroForm = () => {
  const navigate = useNavigate();
  const { mutate: registrar, error } = useRegistrarCliente();
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<FormRegistro>({ resolver: zodResolver(schema) });

  const submit = (data: FormRegistro) => {
    registrar(data, { onSuccess: () => navigate("/login") });
  };

  if (error) throw error;

  return (
    <form onSubmit={handleSubmit(submit)} autoComplete="off">
      <div className="mb-2">
        <label className="form-label">Conta</label>
        <input
          {...register("conta")}
          className={errors.conta ? "form-control is-invalid" : "form-control"}
          type="text"
        />
        <div className="invalid-feedback">{errors.conta?.message}</div>
      </div>
      <div className="mb-2">
        <label className="form-label">Senha</label>
        <input
          {...register("senha")}
          className={errors.senha ? "form-control is-invalid" : "form-control"}
          type="password"
        />
        <div className="invalid-feedback">{errors.senha?.message}</div>
      </div>
      <div className="mb-3">
        <label className="form-label">Confirmar Senha</label>
        <input
          {...register("confirmacaoSenha")}
          className={errors.confirmacaoSenha ? "form-control is-invalid" : "form-control"}
          type="password"
        />
        <div className="invalid-feedback">{errors.confirmacaoSenha?.message}</div>
      </div>
      <button className="btn btn-primary" type="submit">
        Registrar
      </button>
    </form>
  );
};

export default RegistroForm;
