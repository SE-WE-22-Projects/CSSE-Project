import { jwtDecode } from "jwt-decode";

type Role = "USER" | "STAFF" | "ADMIN"

export interface UserToken {
    username: string,
    email: string,
    role: Role[],
    exp: number,
}

export const logout = () => {
    localStorage.removeItem("jwt");
}

export const getUser = (): UserToken | null => {
    let tokenData = localStorage.getItem("jwt");
    if (!tokenData) {
        return null;
    }

    try {
        var token: UserToken = jwtDecode<UserToken>(tokenData);
    } catch (e) {
        return null;
    }

    if (token.exp < Date.now() / 1000) {
        return null;
    }


    return token;
}