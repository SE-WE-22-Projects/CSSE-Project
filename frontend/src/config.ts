import axios from "axios";
import { Configuration, DefaultApi } from "./api";

axios.interceptors.request.use(async (cfg) => {
    const jwt = localStorage.getItem("auth");
    if (jwt) {
        cfg.headers.Authorization = `Bearer ${jwt}`;
    }
    return cfg;
});

export const API = new DefaultApi(new Configuration({
    basePath: "http://localhost:8080"
}));

