import { Route } from "./Dashboard";
import { Apartment, Home, Hotel, LocalHospital, MedicalServices } from "@mui/icons-material";
import WardPage from "./systems/ward/Ward";
import DepartmentPage from "./systems/department/Department";
import PatientPage from "./systems/patient/Patient";
import DoctorPage from "./systems/doctor/Doctor";

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
    }
];
