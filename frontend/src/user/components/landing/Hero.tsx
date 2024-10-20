import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Container from '@mui/material/Container';
import Stack from '@mui/material/Stack';
import Typography from '@mui/material/Typography';
import sideImage from "../../../assets/doctor_image.png";
import { Link as NavLink } from "react-router-dom";

export default function Hero() {
  return (
    <Box
      id="hero"
      sx={(theme) => ({
        width: '100%',
        backgroundRepeat: 'no-repeat',

        backgroundImage:
          'radial-gradient(ellipse 80% 50% at 50% -20%, hsl(210, 100%, 90%), transparent)',
        ...theme.applyStyles('dark', {
          backgroundImage:
            'radial-gradient(ellipse 80% 50% at 50% -20%, hsl(210, 100%, 16%), transparent)',
        }),
      })}
    >
      <Container
        sx={{
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
          pt: { xs: 14, sm: 20 },
          pb: { xs: 8, sm: 12 },
        }}
      >
        <Stack
          spacing={2}
          useFlexGap
          sx={{ alignItems: 'center', width: { xs: '100%', sm: '90%' } }}
        >
          <Stack direction="row" alignItems="center">
            <Box>
              <Typography
                variant="h1"
                sx={{
                  display: 'flex',
                  flexDirection: { xs: 'column', sm: 'row' },
                  alignItems: 'center',
                  alignContent: 'center',
                  justifyContent: 'center',
                  textAlign: "center",
                  fontSize: 'clamp(2rem, 10vw, 2.5rem)',
                }}
              >
                Providing Quality Healthcare For A Brighter And Healthy Future
              </Typography>
              <Typography
                sx={{
                  pt: 4,
                  textAlign: 'center',
                  color: 'text.secondary',
                }}
              >
                At Our Hospital, We Are Dedicated To Providing Exceptional  Medical Care To Our Patients And Their Families. Our Experienced Team Of Medical Professionals. Cutting Edge Technology, And Compassionate Approach Make Us A Leader In The Healthcare Industry.
              </Typography>
              <Stack
                spacing={1}
                alignContent={"center"}
                useFlexGap
                direction="row"
                justifyContent="center"
                sx={{ pt: 8 }}
              >
                <NavLink to="/appointment">
                  <Button
                    variant="contained"
                    color="primary"
                    size="small"
                    sx={{ minWidth: 'fit-content' }}
                  >
                    Book Appointment
                  </Button>
                </NavLink>

              </Stack>


            </Box>
            <img src={sideImage} style={{ maxWidth: "35vw" }} />
          </Stack>

        </Stack>
      </Container>
    </Box>
  );
}
