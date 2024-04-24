import APlusLogo from "../assets/A+.jpg";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import axios from "axios";
import { jwtDecode, JwtPayload } from "jwt-decode";
import { useNavigate } from "react-router-dom";

interface CustomJwtPayload extends JwtPayload {
  role: string;
}

const LoginForm = () => {
  const navigate = useNavigate();

  const loginSchema = z.object({
    username: z.string().min(1, { message: "Username is required" }),
    password: z.string().min(1, { message: "Password is required" }),
  });

  type Login = z.infer<typeof loginSchema>;

  const {
    register,
    handleSubmit,
    formState: { errors, isValid },
  } = useForm<Login>({
    resolver: zodResolver(loginSchema),
  });

  const onSubmit = (data: Login) => {
    axios
      .post("http://localhost:8085/users/login", data)
      .then((response) => {
        const token = response.data.accessToken;
        const decodedToken = jwtDecode<CustomJwtPayload>(token);
        const role = decodedToken.role;
        console.log(role);
        if (role === "ADMINISTRATOR") {
          navigate("/teacher-dashboard");
        } else if (role === "STUDENT") {
          navigate("/student-dashboard");
        }
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <div>
      <img src={APlusLogo} alt="A+ logo" width={100} height={100} />
      <h1>Login Form</h1>
      <form onSubmit={handleSubmit(onSubmit)}>
        <div className="mb-3">
          <label htmlFor="username" className="form-label">
            Username
          </label>
          <input
            {...register("username")}
            id="username"
            className="form-control"
            type="text"
            placeholder="Username"
          />
          {errors.username && (
            <p className="text-danger">{errors.username.message}</p>
          )}
        </div>
        <div className="mb-3">
          <label htmlFor="password" className="form-label">
            Password
          </label>
          <input
            {...register("password")}
            id="password"
            className="form-control"
            type="password"
            placeholder="Password"
          />
          {errors.password && (
            <p className="text-danger">{errors.password.message}</p>
          )}
        </div>
        <button disabled={!isValid} className="btn btn-primary" type="submit">
          Login
        </button>
      </form>
    </div>
  );
};

export default LoginForm;
