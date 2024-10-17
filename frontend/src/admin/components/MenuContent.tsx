import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Stack from '@mui/material/Stack';
import SettingsRoundedIcon from '@mui/icons-material/SettingsRounded';
import InfoRoundedIcon from '@mui/icons-material/InfoRounded';
import HelpRoundedIcon from '@mui/icons-material/HelpRounded';
import { SystemRoutes } from '../Dashboard';
import { Link, useLocation } from 'react-router-dom';


const secondaryListItems = [
  { text: 'Settings', icon: <SettingsRoundedIcon /> },
  { text: 'About', icon: <InfoRoundedIcon /> },
  { text: 'Feedback', icon: <HelpRoundedIcon /> },
];

export default function MenuContent({ routes }: { routes: SystemRoutes }) {
  let location = useLocation();

  return (
    <Stack sx={{ flexGrow: 1, p: 1, justifyContent: 'space-between' }}>
      <List dense>
        {routes.routes.map((item, index) => {
          const path = `/${routes.basePath}/${item.path}`
          return <Link key={index} to={path} style={{ textDecoration: "none", color: "unset" }}>
            <ListItem disablePadding sx={{ display: 'block' }}>
              <ListItemButton selected={location.pathname.startsWith(path)}>
                <ListItemIcon>{item.display?.icon}</ListItemIcon>
                <ListItemText primary={item.display?.title} />
              </ListItemButton>
            </ListItem>
          </Link>
        })}
      </List>

      <List dense>
        {secondaryListItems.map((item, index) => (
          <ListItem key={index} disablePadding sx={{ display: 'block' }}>
            <ListItemButton>
              <ListItemIcon>{item.icon}</ListItemIcon>
              <ListItemText primary={item.text} />
            </ListItemButton>
          </ListItem>
        ))}
      </List>
    </Stack>
  );
}
