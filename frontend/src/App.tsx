import { Box } from '@mui/material'
import './App.css'
import { Configuration, DefaultApi } from './api';
import DashboardApp from './admin/App';

export const API = new DefaultApi(new Configuration({
  basePath: "http://localhost:8080", accessToken: async () => {
    const jwt = localStorage.getItem("auth");
    return jwt || "";
  }
}));

function App() {
  return <Box sx={{ minWidth: "100vw", height: "100vh" }}>
    <DashboardApp />
  </Box>
}

export default App
