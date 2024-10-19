import Divider from '@mui/material/Divider';
import AppAppBar from './components/landing/AppAppBar';
import Hero from './components/landing/Hero';
import LogoCollection from './components/landing/LogoCollection';
import FAQ from './components/landing/FAQ';
import Footer from './components/landing/Footer';
import Highlights from './components/landing/Highlights';
import Testimonials from './components/landing/Testimonials';
import Features from './components/landing/Features';
import Pricing from './components/landing/Pricing';

export default function LandingPage() {
  return <>
    <AppAppBar isHome />
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
  </>

}
