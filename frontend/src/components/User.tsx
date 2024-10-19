import { createContext, ReactNode, useContext, useMemo, useState } from 'react';
import { jwtDecode } from "jwt-decode";

type Role = "USER" | "STAFF" | "ADMIN"

export interface UserToken {
    username: string,
    email: string,
    role: Role[],
    isAdmin: boolean
    isStaff: boolean
    exp: number,
}

interface UserControls {
    logout: () => void
    setToken: (token: string) => void
    user: UserToken | null
    loggedIn: boolean
}

const getUser = (): UserToken | null => {
    let tokenData = localStorage.getItem("auth");
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

    token.isAdmin = !!token.role.find(v => v === "ADMIN")
    token.isStaff = !!token.role.find(v => v === "STAFF")


    return token;
}


const logout = () => {
    localStorage.removeItem("auth");
}

const setToken = (token: string) => {
    localStorage.setItem("auth", token);
}

const UserContext = createContext<UserControls>({
    logout: logout,
    setToken: setToken,
    user: null,
    loggedIn: false
});


export const useUser = () => { return useContext(UserContext) };

export const UserProvider = ({ children }: { children: ReactNode | ReactNode[] }) => {
    const [user, setUser] = useState<UserToken | null>(getUser());


    const controls = useMemo<UserControls>(() => {
        return {
            logout: () => {
                logout()
                setUser(null);
            },
            setToken: (token) => {
                setToken(token);

                const newUser = getUser();
                if (newUser === null) console.error("Invalid login token");
                setUser(newUser);
            },
            user: user,
            loggedIn: user != null
        }
    }, [user])

    return (
        <UserContext.Provider value={controls}>
            {children}
        </UserContext.Provider>
    );

}