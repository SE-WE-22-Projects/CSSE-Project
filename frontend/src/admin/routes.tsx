import { Dashboard, Route } from "./components/Dashboard";
import { Apartment, BookmarkAdd, DateRange, Hotel, LocalHospital, MedicalInformation, MedicalServices } from "@mui/icons-material";
import WardPage from "./pages/admin/Ward";
import DepartmentPage from "./pages/admin/Department";
import PatientPage from "./pages/admin/Patient";
import DoctorPage from "./pages/admin/Doctor";
import MedicalServicePage from "./pages/admin/MedicalService";
import SchedulePage from "./pages/admin/Schedule";
import AppointmentPage from "./pages/admin/Appointment";
import Login from "../user/Login";
import PatientList from "./pages/staff/patients/List";
import PatientDetails from "./pages/staff/patients/Details";

export const DashboardRoutes: Route[] = [
    {
        element: <PatientList />,
        path: "patients",
        display: {
            title: "Patient Details",
            icon: <Hotel />,
            admin: false
        },
    },
    {
        path: "patients/:id",
        element: <PatientDetails />
    },
    {
        element: <WardPage />,
        path: "ward",
        display: {
            title: "Wards",
            icon: <LocalHospital />,
            admin: true
        }
    },
    {
        element: <DepartmentPage />,
        path: "department",
        display: {
            title: "Departments",
            icon: <Apartment />,
            admin: true
        }
    },
    {
        element: <PatientPage />,
        path: "patient",
        display: {
            title: "Patient",
            icon: <Hotel />,
            admin: true
        }
    },
    {
        element: <DoctorPage />,
        path: "doctor",
        display: {
            title: "Doctors",
            icon: <MedicalServices />,
            admin: true
        }
    },
    {
        element: <MedicalServicePage />,
        path: "service",
        display: {
            title: "Service",
            icon: <MedicalInformation />,
            admin: true
        }
    },
    {
        element: <SchedulePage />,
        path: "schedule",
        display: {
            title: "Schedule",
            icon: <DateRange />,
            admin: true
        }
    },
    {
        element: <AppointmentPage />,
        path: "appointment",
        display: {
            title: "Appointment",
            icon: <BookmarkAdd />,
            admin: true
        }
    }
];


export const Routes = [
    {
        path: "/dashboard",
        element: <Dashboard routes={{ title: "HealthSphere Dashboard", basePath: "dashboard", routes: DashboardRoutes }} />,
        children: [...DashboardRoutes],
    },
    {
        path: "/login", element: <Login />
    }
]