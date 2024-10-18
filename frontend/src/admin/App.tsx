
import { ConfirmProvider } from "material-ui-confirm";
import { SnackbarProvider } from "notistack";
import { CssBaseline } from "@mui/material";
import AppTheme from "../shared/theme/AppTheme";
import { createBrowserRouter, RouterProvider } from "react-router-dom";

import { Dashboard } from "./Dashboard";
import { Routes } from "./routes";
import { LocalizationProvider } from "@mui/x-date-pickers";
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs'
import Login from "./Login";


const router = createBrowserRouter([
    {
        path: "/admin",
        element: <Dashboard routes={{ title: "HealthSphere", basePath: "admin", routes: Routes, dashboard: <></> }} />,
        children: [...Routes],
    },
    { path: "/login", element: <Login /> }
]);

const DashboardApp = () => {
    return (
        <AppTheme>
            <LocalizationProvider dateAdapter={AdapterDayjs}>
                <CssBaseline enableColorScheme />
                <ConfirmProvider>
                    <SnackbarProvider>
                        <RouterProvider router={router} />
                    </SnackbarProvider>
                </ConfirmProvider>
            </LocalizationProvider>
        </AppTheme>
    )
}

export default DashboardApp