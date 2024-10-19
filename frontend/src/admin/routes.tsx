import { Dashboard, Route } from "./components/Dashboard";
import { Apartment, BookmarkAdd, DateRange, Hotel, LocalHospital, MedicalInformation, MedicalServices } from "@mui/icons-material";
import WardPage from "./systems/ward/Ward";
import DepartmentPage from "./systems/department/Department";
import PatientPage from "./systems/patient/Patient";
import DoctorPage from "./systems/doctor/Doctor";
import MedicalServicePage from "./systems/medicalService/MedicalService";
import SchedulePage from "./systems/schedule/Schedule";
import AppointmentPage from "./systems/appointment/Appointment";
import Login from "../user/Login";

export const DashboardRoutes: Route[] = [
    {
        element: <WardPage />,
        path: "ward",
        display: {
            title: "Wards",
            icon: <LocalHospital />
        }
    },
    {
        element: <DepartmentPage />,
        path: "department",
        display: {
            title: "Departments",
            icon: <Apartment />
        }
    },
    {
        element: <PatientPage />,
        path: "patient",
        display: {
            title: "Patient",
            icon: <Hotel />
        }
    },
    {
        element: <DoctorPage />,
        path: "doctor",
        display: {
            title: "Doctors",
            icon: <MedicalServices />
        }
    },
    {
        element: <MedicalServicePage />,
        path: "service",
        display: {
            title: "Service",
            icon: <MedicalInformation />
        }
    },
    {
        element: <SchedulePage />,
        path: "schedule",
        display: {
            title: "Schedule",
            icon: <DateRange />
        }
    },
    {
        element: <AppointmentPage />,
        path: "appointment",
        display: {
            title: "Appointment",
            icon: <BookmarkAdd />
        }
    }
];


export const Routes = [
    {
        path: "/dashboard",
        element: <Dashboard routes={{ title: "HealthSphere", basePath: "dashboard", routes: DashboardRoutes, dashboard: <></> }} />,
        children: [...DashboardRoutes],
    },
    {
        path: "/login", element: <Login />
    }
]