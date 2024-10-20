import { RouteObject } from "react-router-dom";
import LandingPage from "./LandingPage";
import Appointment from "./appointment/Appointment";
import AppointmentList from "./appointment/AppointmentList";
import PageLayout from "./components/PageLayout";



const UserRoutes: RouteObject[] = [
    { index: true, element: <LandingPage /> },
    { path: "/appointment", element: <Appointment /> },
    { path: "/appointment/list", element: <AppointmentList /> },
];
export const Routes: RouteObject[] = [
    {
        path: "",
        element: <PageLayout />,
        children: UserRoutes,
    }
]