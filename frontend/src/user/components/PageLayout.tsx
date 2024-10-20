import { Box } from '@mui/material';
import AppAppBar from './landing/AppAppBar';
import Footer from './landing/Footer';
import { Outlet } from 'react-router-dom';

export default function PageLayout() {
  return <>
    <Box sx={{ width: "100%", minHeight: "100%", display: "flex", flexDirection: "column" }}>
      <AppAppBar />
      <Box flexGrow="1">
        <Outlet />
      </Box>
      <Footer />
    </Box>
  </>

}
