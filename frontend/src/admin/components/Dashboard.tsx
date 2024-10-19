import type { } from '@mui/x-date-pickers/themeAugmentation';
import type { } from '@mui/x-data-grid/themeAugmentation';
import { alpha } from '@mui/material/styles';
import Stack from '@mui/material/Stack';
import Header from './dashboard/Header';
import SideMenu from './dashboard/SideMenu';
import { Link as MUILink, Paper, Typography } from '@mui/material';
import { Box } from '@mui/material';
import { ReactNode, useEffect } from 'react';
import { NonIndexRouteObject, Outlet } from 'react-router-dom'

/**
 * A object that contains all routes in a specific system.
 */
export interface SystemRoutes {
  /**
   * The title of the page. This will be displayed next to the logo image
   */
  title: string;
  /**
   * Base path for all routes. All routes registered in this system will have this path prefixed.
   */
  basePath: string;


  /**
   * A list of all pages in the system
   */
  routes: Route[];
}

interface Display {
  /**
   * Title to display in the sidebar
   */
  title: string;
  /**
   * An optional icon to display in the sidebar.
   */
  icon?: ReactNode;
}

export interface Route extends NonIndexRouteObject {
  /**
   * Display details for the sidebar. If this is null, this will not be displayed in the sidebar.
   */
  display?: Display;
}

/**
 * Dashboard page component. This is the main layout used for all dashboard pages.
 */
export const Dashboard = ({ routes }: { routes: SystemRoutes }) => {
  useEffect(() => {
    document.title = routes.title
  }, [])

  return <Box sx={{ display: 'flex', height: "100%", minWidth: "90vw" }}>
    <SideMenu routes={routes} />
    {/* Main content */}
    <Box
      component="main"
      sx={(theme: any) => ({
        flexGrow: 1,
        backgroundColor: theme.vars
          ? `rgba(${theme.vars.palette.background.defaultChannel} / 1)`
          : alpha(theme.palette.background.default, 1),
        overflow: 'auto',
      })}>
      <Stack
        spacing={2}
        sx={{
          alignItems: 'center',
          mx: 3,
          pb: 5,
          mt: { xs: 8, md: 0 },
          height: "100%"
        }}>
        <Header />

        <Paper sx={{
          flexGrow: 1,
          m: 2,
          p: 6,
          width: "100%"
        }}>
          <Outlet />
        </Paper>

        <Typography
          variant="body2"
          align="center"
          sx={{
            color: 'text.secondary',
            my: 4,
          }}>

          {'Copyright © '}
          <MUILink color="inherit" href="/">
            HealthSphere
          </MUILink>{' '}
          {new Date().getFullYear()}
          {'.'}
        </Typography>
      </Stack>
    </Box>
  </Box>;
}; 