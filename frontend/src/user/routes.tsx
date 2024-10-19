import { RouteObject } from "react-router-dom";
import LandingPage from "./LandingPage";
import Appointment from "./appointment/Appointment";
import AppointmentList from "./appointment/AppointmentList";

export const Routes: RouteObject[] = [
    { index: true, element: <LandingPage /> },
    {path: "/appointment", element: <AppointmentList/>}
];