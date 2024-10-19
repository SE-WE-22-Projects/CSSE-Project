import { Avatar, Box, Card, CardContent, Typography, Button, Grid, Divider } from '@mui/material';
import { deepPurple } from '@mui/material/colors';
import AppAppBar from '../components/landing/AppAppBar';
import { useEffect, useState } from 'react';
import { API } from '../../config';
import { Doctor, Patient } from '../../api';

const ProfilePage = () => {
    const [userDetails, setUserDetails] = useState<Patient & Doctor>({});
    const getUserDetails = async () => {
        const response = await API.getLoggedPerson();
        setUserDetails(response.data);
    }
    useEffect(() => {
        getUserDetails();
    }, []);

    return (
        <>
            <AppAppBar />
            <Box
                sx={{
                    display: 'flex',
                    justifyContent: 'center',
                    alignItems: 'center',
                    minHeight: '100vh',
                    
                    padding: 2,
                }}
            >
                <Card
                    sx={{
                        maxWidth: 600,
                        width: '100%',
                        padding: 3,
                        boxShadow: 3,
                        borderRadius: '16px',
                    }}
                >
                    <CardContent>
                        <Grid container spacing={2} justifyContent="center">
                            <Grid item xs={12} sx={{ display: 'flex', justifyContent: 'center' }}>
                                <Avatar sx={{ bgcolor: deepPurple[500], width: 100, height: 100 }}>A</Avatar>
                            </Grid>
                            <Grid item xs={12} sx={{ textAlign: 'center' }}>
                                <Typography variant="h5" sx={{ fontWeight: 'bold' }}>
                                    {userDetails.name}
                                </Typography>
                                <Typography variant="body1" color="textSecondary">
                                    {userDetails.role!}
                                </Typography>
                            </Grid>

                            <Grid item xs={12}>
                                <Divider sx={{ marginY: 2 }} />
                            </Grid>

                            <Grid item xs={12}>
                                <Typography variant="h6" sx={{ fontWeight: 'bold' }}>
                                    General Information
                                </Typography>
                                <Typography variant="body2" color="textSecondary" paragraph>
                                    Gender: {userDetails.gender}
                                </Typography>
                                <Typography variant="body2" color="textSecondary" paragraph>
                                    Birth Date: {userDetails.dob}
                                </Typography>
                                {userDetails.role === 'STAFF' ?
                                <Typography variant="body2" color="textSecondary">
                                    Registration No.: {userDetails.regNo}
                                </Typography> 
                                : null}
                            </Grid>

                            <Grid item xs={12}>
                                <Typography variant="h6" sx={{ fontWeight: 'bold' }}>
                                    Contact Information
                                </Typography>
                                <Typography variant="body2" color="textSecondary">
                                    Email: {userDetails.email}
                                </Typography>
                                <Typography variant="body2" color="textSecondary">
                                    Contact No.: {userDetails.phoneNo}
                                </Typography>
                                {userDetails.role === 'USER' ?
                                    <Typography variant="body2" color="textSecondary">
                                        Emergency Contact No: {userDetails.emergencyContactNo}
                                    </Typography> : null
                                }

                            </Grid>

                            <Grid item xs={12} sx={{ textAlign: 'center', marginTop: 2 }}>
                                <Button variant="contained" color="primary" sx={{ marginRight: 1 }}>
                                    Edit Profile
                                </Button>
                                <Button variant="outlined" color="primary">
                                    Logout
                                </Button>
                            </Grid>
                        </Grid>
                    </CardContent>
                </Card>
            </Box>
        </>
    );
};

export default ProfilePage;