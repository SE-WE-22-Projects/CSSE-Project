import { Route } from "./Dashboard";
import { Apartment, DateRange, Hotel, LocalHospital, MedicalInformation, MedicalServices } from "@mui/icons-material";
import WardPage from "./systems/ward/Ward";
import DepartmentPage from "./systems/department/Department";
import PatientPage from "./systems/patient/Patient";
import DoctorPage from "./systems/doctor/Doctor";
import MedicalServicePage from "./systems/medicalService/MedicalService";
import SchedulePage from "./systems/schedule/Schedule";

export const Routes: Route[] = [
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
    }
];
