import CssBaseline from '@mui/material/CssBaseline';
import Divider from '@mui/material/Divider';
import AppAppBar from './components/landing/AppAppBar';
import AppTheme from '../theme/AppTheme';
import Hero from './components/landing/Hero';
import LogoCollection from './components/landing/LogoCollection';
import FAQ from './components/landing/FAQ';
import Footer from './components/landing/Footer';
import Highlights from './components/landing/Highlights';
import Testimonials from './components/landing/Testimonials';
import Features from './components/landing/Features';
import Pricing from './components/landing/Pricing';

export default function LandingPage() {
  return (

    <AppTheme>
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
    </AppTheme>
  );
}
