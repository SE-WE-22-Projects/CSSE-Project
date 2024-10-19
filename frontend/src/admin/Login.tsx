import CssBaseline from '@mui/material/CssBaseline';
import Paper from '@mui/material/Paper';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { enqueueSnackbar } from 'notistack';
import { getUser } from '../util/user';
import { API } from '../config';
import DataForm, { Field } from '../components/DataForm';
import { LoginRequest } from '../api';
import SiteLogo from '../components/Logo';

const LoginFields = {
    email: Field.email("Email"),
    password: Field.text("Password", { hidden: true })
}


export default function Login() {
    const navigate = useNavigate();

    const handleSubmit = async (data: LoginRequest) => {
        try {
            let response = await API.login(data);
            localStorage.setItem("jwt", response.data);


        } catch (e: any) {
            if (e?.response?.status === 400) {
                enqueueSnackbar("Invalid username or password", { variant: "error" })
            } else {
                enqueueSnackbar("Failed to login", { variant: "error" })
            }
            console.error(e);
        }
    };

    const redirect = () => {
        const user = getUser();
        if (user !== null) {
            if (!!user.role.find(v => v === "STAFF")) {
                navigate('/admin')
            } else {
                navigate('/')
            }
        }
    }


    useEffect(redirect, [])

    return (
        <Grid container component="main" sx={{ height: '100vh' }}>
            <CssBaseline />
            <Grid
                item
                xs={false}
                sm={4}
                md={7}
                sx={{
                    backgroundColor: '#003a2b',
                    alignContent: "center"
                }}
            >
            </Grid>
            <Grid item xs={12} sm={8} md={5} component={Paper} elevation={6} square>
                <Box
                    sx={{
                        my: 8,
                        mx: 4,
                        display: 'flex',
                        flexDirection: 'column',
                        alignItems: 'center',
                    }}
                >
                    <Box sx={{ pb: "32px", fontSize: "1.5rem" }}>
                        <SiteLogo />
                    </Box>

                    <Typography component="h1" variant="h5">
                        Sign in
                    </Typography>
                    <Box sx={{ mt: 1 }}>
                        <DataForm fields={LoginFields} onSubmit={handleSubmit} submitText='Login' />
                    </Box>
                </Box>
            </Grid>
        </Grid>
    );
}