import { RouteObject } from "react-router-dom";
import LandingPage from "./LandingPage";
import Appointment from "./appointment/Appointment";
import AppointmentList from "./appointment/AppointmentList";
import PageLayout from "./components/PageLayout";
import BillView from "./appointment/BillView";



const UserRoutes: RouteObject[] = [
    { index: true, element: <LandingPage /> },
    { path: "/appointment", element: <Appointment /> },
    { path: "/appointment/list", element: <AppointmentList /> },
    { path: "/appointment/list/:id", element: <BillView /> },
];
export const Routes: RouteObject[] = [
    {
        path: "",
        element: <PageLayout />,
        children: UserRoutes,
    }
]