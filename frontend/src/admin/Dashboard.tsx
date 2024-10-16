import type { } from '@mui/x-date-pickers/themeAugmentation';
import type { } from '@mui/x-data-grid/themeAugmentation';
import { alpha } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import Box from '@mui/material/Box';
import Stack from '@mui/material/Stack';
import Header from './components/Header';
import SideMenu from './components/SideMenu';
import AppTheme from '../shared-theme/AppTheme';
import { Outlet } from 'react-router-dom';
import { Link as MUILink, Typography } from '@mui/material';


export default function Dashboard(props: { disableCustomTheme?: boolean }) {
  return (
    <AppTheme {...props} >
      <CssBaseline enableColorScheme />
      <Box sx={{ display: 'flex', minHeight: "90vh", minWidth: "90vw" }}>
        <SideMenu />
        {/* Main content */}
        <Box
          component="main"
          sx={(theme: any) => ({
            flexGrow: 1,
            backgroundColor: theme.vars
              ? `rgba(${theme.vars.palette.background.defaultChannel} / 1)`
              : alpha(theme.palette.background.default, 1),
            overflow: 'auto',
          })}
        >
          <Stack
            spacing={2}
            sx={{
              alignItems: 'center',
              mx: 3,
              pb: 5,
              mt: { xs: 8, md: 0 },
            }}
          >
            <Header />
            <Box flexGrow={1}>
              <Outlet />
            </Box>

            <Typography
              variant="body2"
              align="center"
              sx={{
                color: 'text.secondary',
                my: 4,
              }}
            >
              {'Copyright © '}
              <MUILink color="inherit" href="https://mui.com/">
                HealthSphere
              </MUILink>{' '}
              {new Date().getFullYear()}
              {'.'}
            </Typography>
          </Stack>
        </Box>
      </Box>
    </AppTheme>
  );
}
