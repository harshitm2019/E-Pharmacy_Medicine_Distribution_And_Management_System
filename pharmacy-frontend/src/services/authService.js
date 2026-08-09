import api from "../api/api";

export async function register(registerRequest) {

   return (await api.post("/auth/register", registerRequest)).data;

}

export async function login(loginRequest) {

     return (await api.post("/auth/login", loginRequest)).data;

}