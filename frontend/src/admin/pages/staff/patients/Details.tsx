import { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { Admission, Patient } from '../../../../api';
import { Box, List, ListItem, ListItemText, Stack, ToggleButton, ToggleButtonGroup, Typography } from '@mui/material';
import { DNA } from 'react-loader-spinner';
import { API } from '../../../../config';
import { enqueueSnackbar } from 'notistack';
import { PageTitle } from '../../../../components/Logo';
import { AccountCircle, Bed, History } from '@mui/icons-material';

const PatientDetails = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [patient, setPatient] = useState<Patient>();
    const [admissions, setAdmissions] = useState<Admission[]>();
    const [loading, setLoading] = useState(true);

    const [page, setPage] = useState<'details' | 'admission' | 'history'>('details');

    const handlePage = (_: any, value: string | null,) => {
        if (value != null)
            setPage(value as any);
    }

    const loadPatient = (async () => {
        if (id === undefined || !/^\d+$/.test(id)) {
            navigate("/dashboard/patients");
            enqueueSnackbar("Invalid patient Selection", { variant: "error" })
            return;
        }

        setLoading(true);
        let patientRes = await API.getPatientById(Number.parseInt(id));
        setPatient(patientRes.data);

        let admissionRes = await API.findAdmissionsByPatient(Number.parseInt(id));
        setAdmissions(admissionRes.data);

        setLoading(false);
    });

    useEffect(() => {
        loadPatient()
    }, [id]);

    if (loading || patient === undefined) {
        return <Stack direction="column" alignItems="center" sx={{ width: "100%" }}>
            <DNA
                visible={true}
                height="30vh"
                width="30vw"
                ariaLabel="dna-loading"
                wrapperStyle={{}}
                wrapperClass="dna-wrapper"
            />
            <Typography sx={{ fontSize: "1.4em" }}>Loading...</Typography>
        </Stack>
    }

    return <>
        <Box mb={4}>
            <ToggleButtonGroup
                value={page}
                exclusive
                onChange={handlePage}
            >
                <ToggleButton value='details'>
                    <AccountCircle /> Patient Details
                </ToggleButton>
                <ToggleButton value='admission'>
                    <Bed /> Admission
                </ToggleButton>
                <ToggleButton value='history'>
                    <History /> Medical History
                </ToggleButton>
            </ToggleButtonGroup>
        </Box>

        {page === 'details' ?
            <>
                <PageTitle>Patient Details</PageTitle>
                <List dense >
                    <ListItem>
                        <ListItemText primary="Name" secondary={patient.name} />
                    </ListItem>
                    <ListItem>
                        <ListItemText primary="Date of Birth" secondary={patient.dob} />
                    </ListItem>
                    <ListItem>
                        <ListItemText primary="Gender" secondary={patient.gender} />
                    </ListItem>
                    <ListItem>
                        <ListItemText primary="Phone Number" secondary={patient.phoneNo} />
                    </ListItem>
                    <ListItem>
                        <ListItemText primary="Emergency Contact" secondary={patient.emergencyContactNo} />
                    </ListItem>
                    <ListItem>
                        <ListItemText primary="Address" secondary={patient.address} />
                    </ListItem>
                </List>
            </>
            : null}

        {page === "history" ? admissions?.map(v => JSON.stringify(v)) : null}
    </>
}

export default PatientDetails;