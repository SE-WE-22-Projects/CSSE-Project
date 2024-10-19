import { styled } from '@mui/material/styles';
import MuiDrawer, { drawerClasses } from '@mui/material/Drawer';
import Box from '@mui/material/Box';
import Divider from '@mui/material/Divider';
import MenuContent from './MenuContent';
import SiteLogo from '../../../components/Logo';
import { SystemRoutes } from '../Dashboard';
import UserCard from './UserCard';
import { Link } from 'react-router-dom';

const drawerWidth = 240;

const Drawer = styled(MuiDrawer)({
  width: drawerWidth,
  flexShrink: 0,
  boxSizing: 'border-box',
  mt: 10,
  [`& .${drawerClasses.paper}`]: {
    width: drawerWidth,
    boxSizing: 'border-box',
  },
});



export default function SideMenu({ routes }: { routes: SystemRoutes }) {

  return (
    <Drawer
      variant="permanent"
      sx={{
        display: { xs: 'none', md: 'block' },
        [`& .${drawerClasses.paper}`]: {
          backgroundColor: 'background.paper',
        },
      }}
    >
      <Link to="/">
        <Box sx={{ py: "12px", px: '12px' }}>
          <SiteLogo />
        </Box>
      </Link>

      <Divider />

      <MenuContent routes={routes} />


      <UserCard />
    </Drawer>
  );
}
