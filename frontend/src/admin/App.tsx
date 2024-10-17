
import { ConfirmProvider } from "material-ui-confirm";
import { SnackbarProvider } from "notistack";
import { CssBaseline } from "@mui/material";
import AppTheme from "../shared/theme/AppTheme";
import { createBrowserRouter, RouterProvider } from "react-router-dom";

import { Dashboard } from "./Dashboard";
import { Routes } from "./routes";


/**
 * Appointment
 * Schedule
 * Staff
 * Finance
 * Report
 * Diagnosis
 * 
 * Patient
 */


const router = createBrowserRouter([
    {
        path: "/admin",
        element: <Dashboard routes={{ title: "HealthSphere", basePath: "admin", routes: Routes, dashboard: <></> }} />,
        children: [...Routes],
    },
]);

const DashboardApp = () => {
    return (
        <AppTheme>
            <CssBaseline enableColorScheme />
            <ConfirmProvider>
                <SnackbarProvider>
                    <RouterProvider router={router} />
                </SnackbarProvider>
            </ConfirmProvider>
        </AppTheme>
    )
}

export default DashboardApp