import { Route } from "./Dashboard";
import { Home } from "@mui/icons-material";
import WardPage from "./systems/ward/Ward";

export const Routes: Route[] = [
    {
        element: <WardPage />,
        path: "ward",
        display: {
            title: "Ward Management",
            icon: <Home />
        }
    }
];
