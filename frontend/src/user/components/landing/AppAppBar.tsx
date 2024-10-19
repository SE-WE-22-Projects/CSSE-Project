import { styled, alpha } from '@mui/material/styles';
import Box from '@mui/material/Box';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Button from '@mui/material/Button';
import Container from '@mui/material/Container';
import SiteLogo from '../../../components/Logo';
import ColorModeIconDropdown from '../../../theme/ColorModeIconDropdown';
import { useUser } from '../../../components/User';
import { Link, useNavigate } from 'react-router-dom';
import UserCard from '../../../admin/components/dashboard/UserCard';

const StyledToolbar = styled(Toolbar)(({ theme }) => ({
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'space-between',
  flexShrink: 0,
  borderRadius: `calc(${theme.shape.borderRadius}px + 8px)`,
  backdropFilter: 'blur(24px)',
  border: '1px solid',
  borderColor: theme.palette.divider,
  backgroundColor: alpha(theme.palette.background.default, 0.4),
  boxShadow: theme.shadows[1],
  padding: '8px 12px',
}));

export default function AppAppBar() {
  const user = useUser();
  const navigate = useNavigate();

  return (
    <AppBar
      position="fixed"
      sx={{ boxShadow: 0, bgcolor: 'transparent', backgroundImage: 'none', mt: 1 }}
    >
      <Container maxWidth="lg">
        <StyledToolbar variant="dense" disableGutters>
          <Box sx={{ flexGrow: 1, display: 'flex', alignItems: 'center', px: 0 }}>
            <SiteLogo />
            <Box sx={{ display: 'flex' }}>
              <Button variant="text" color="info" size="small">
                Features
              </Button>
              <Button variant="text" color="info" size="small">
                Testimonials
              </Button>
              <Button variant="text" color="info" size="small">
                Highlights
              </Button>
              <Button variant="text" color="info" size="small">
                Pricing
              </Button>
              <Button variant="text" color="info" size="small" >
                FAQ
              </Button>
              <Button variant="text" color="info" size="small" >
                Blog
              </Button>
            </Box>
          </Box>
          <Box
            sx={{
              display: { xs: 'none', md: 'flex' },
              gap: 1,
              alignItems: 'center',
            }}
          >
            {
              user.user?.isStaff ? <Button variant="text" color="info" size="small" onClick={() => navigate("/admin")}>
                Dashboard
              </Button> : null
            }
            {
              user.loggedIn ?
                <>
                  <Link to="/appointment">
                    <Button color="primary" variant="text" size="small">
                      Appointment
                    </Button>
                  </Link>
                  <UserCard small /> </> :
                <>
                  <Link to="/login">
                    <Button color="primary" variant="text" size="small">
                      Sign in
                    </Button>
                  </Link>
                  <Link to="/register">
                    <Button color="primary" variant="contained" size="small">
                      Sign up
                    </Button>
                  </Link>
                </>
            }
            <ColorModeIconDropdown />

          </Box>

        </StyledToolbar>
      </Container>
    </AppBar>
  );
}
