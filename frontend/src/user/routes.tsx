import { RouteObject } from "react-router-dom";
import LandingPage from "./LandingPage";
import Appointment from "./appointment/Appointment";

export const Routes: RouteObject[] = [
    { index: true, element: <LandingPage /> },
    {path: "/appointment", element: <Appointment/>}
];