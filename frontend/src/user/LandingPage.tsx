import * as React from 'react';
import { PaletteMode, ThemeProvider, createTheme } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import Divider from '@mui/material/Divider';
import AppAppBar from './components/landing/AppAppBar';
import Hero from './components/landing/Hero';
import LogoCollection from './components/landing/LogoCollection';
import Highlights from './components/landing/Highlights';
import Pricing from './components/landing/Pricing';
import Features from './components/landing/Features';
import Testimonials from './components/landing/Testimonials';
import FAQ from './components/landing/FAQ';
import Footer from './components/landing/Footer';
import getMPTheme from './theme/getMPTheme';

export default function LandingPage() {
  const [mode, setMode] = React.useState<PaletteMode>('light');
  const MPTheme = createTheme(getMPTheme(mode));

  // This code only runs on the client side, to determine the system color preference
  React.useEffect(() => {
    // Check if there is a preferred mode in localStorage
    const savedMode = localStorage.getItem('themeMode') as PaletteMode | null;
    if (savedMode) {
      setMode(savedMode);
    } else {
      // If no preference is found, it uses system preference
      const systemPrefersDark = window.matchMedia(
        '(prefers-color-scheme: dark)',
      ).matches;
      setMode(systemPrefersDark ? 'dark' : 'light');
    }
  }, []);



  return (

    <ThemeProvider theme={MPTheme}>
      <CssBaseline enableColorScheme />

      <AppAppBar />
      <Hero />
      <div>
        <LogoCollection />
        <Features />
        <Divider />
        <Testimonials />
        <Divider />
        <Highlights />
        <Divider />
        <Pricing />
        <Divider />
        <FAQ />
        <Divider />
        <Footer />
      </div>
    </ThemeProvider>
  );
}
