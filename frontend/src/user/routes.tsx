import { RouteObject } from "react-router-dom";
import LandingPage from "./LandingPage";
import Appointment from "./appointment/Appointment";
import AppointmentList from "./appointment/AppointmentList";
import ProfilePage from "./profile/profilePage";

export const Routes: RouteObject[] = [
    { index: true, element: <LandingPage /> },
    {path: "/appointment", element: <Appointment/>},
    {path: "/appointment/list", element: <AppointmentList/>},
    {path: "/profile", element: <ProfilePage/>},
];