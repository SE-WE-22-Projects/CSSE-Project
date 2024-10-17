import { Configuration, DefaultApi } from "./api";

export const API = new DefaultApi(new Configuration({
    basePath: "http://localhost:8080",
    accessToken: async () => {
        const jwt = localStorage.getItem("auth");
        return jwt || "";
    }
}));
