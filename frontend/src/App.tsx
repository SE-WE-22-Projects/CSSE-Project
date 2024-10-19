import { Box } from '@mui/material'
import { ConfirmProvider } from "material-ui-confirm";
import { SnackbarProvider } from "notistack";
import { CssBaseline } from "@mui/material";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import { LocalizationProvider } from "@mui/x-date-pickers";
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs'
import AppTheme from './theme/AppTheme';

import { Routes as AdminRoutes } from "./admin/routes";
import { Routes as UserRoutes } from "./user/routes";
import { UserProvider } from './components/User';

const router = createBrowserRouter([...AdminRoutes, ...UserRoutes]);


function App() {
  return <Box sx={{ minWidth: "100vw", height: "100vh" }}>
    <AppTheme>
      <UserProvider>
        <LocalizationProvider dateAdapter={AdapterDayjs}>
          <CssBaseline enableColorScheme />
          <ConfirmProvider>
            <SnackbarProvider>
              <RouterProvider router={router} />
            </SnackbarProvider>
          </ConfirmProvider>
        </LocalizationProvider>
      </UserProvider>
    </AppTheme>
  </Box>
}

export default App
