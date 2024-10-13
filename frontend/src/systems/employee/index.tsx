import { RouteObject } from "react-router-dom";
import PageLayout from "./PageLayout";

const routes: { basePath: String; layout: React.ReactNode; routes: RouteObject[] } = {
    basePath: "employee",
    layout: <PageLayout />,
    routes: []
};

export default routes;