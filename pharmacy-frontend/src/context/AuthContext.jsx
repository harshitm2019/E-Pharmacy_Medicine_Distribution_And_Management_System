import { createContext, useState } from "react";

const AuthContext = createContext();

export function AuthProvider({ children }) {

   const [user, setUser] = useState(() => {

    const storedUser = localStorage.getItem("user");

    return storedUser ? JSON.parse(storedUser) : null;

});


const login = (loginResponse) => {

    const user = {

        userId: loginResponse.userId,
        username: loginResponse.username,
        role: loginResponse.role

    };

    localStorage.setItem(
        "token",
        loginResponse.accessToken
    );

    localStorage.setItem(
        "user",
        JSON.stringify(user)
    );

    setUser(user);

};
    const logout = () => {

    localStorage.removeItem("token");
    localStorage.removeItem("user");
    setUser(null);

};

const updateUsername = (username) => {

    setUser(previous => {

        if (!previous) {
            return previous;
        }

        const updatedUser = {
            ...previous,
            username
        };

        localStorage.setItem("user", JSON.stringify(updatedUser));

        return updatedUser;
    });

};

   const isAuthenticated = !!user;

    return (

    <AuthContext.Provider
    value={{
        user,
        login,
        logout,
        updateUsername,
        isAuthenticated
    }}
>
    {children}
    </AuthContext.Provider>

    );
}

export default AuthContext;