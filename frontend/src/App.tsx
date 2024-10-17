import { Box } from '@mui/material'
import './App.css'
import Dashboard from './admin/Dashboard'
import { Configuration, DefaultApi } from './api';

export const API = new DefaultApi(new Configuration({
  basePath: "http://localhost:8080", accessToken: async () => {
    const jwt = localStorage.getItem("auth");
    return jwt || "";
  }
}));

function App() {
  return <Box sx={{ minWidth: "100vw" }}>
    <Dashboard />
  </Box>
}

export default App
