import { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { Admission, Patient } from '../../../../api';
import { Box, Divider, List, ListItem, ListItemText, Stack, ToggleButton, ToggleButtonGroup, Typography } from '@mui/material';
import { DNA } from 'react-loader-spinner';
import { API } from '../../../../config';
import { enqueueSnackbar } from 'notistack';
import { PageTitle } from '../../../../components/Logo';
import { AccountCircle, Bed, History } from '@mui/icons-material';
import TablePage from '../../../components/TablePage';
import { GridColDef } from '@mui/x-data-grid';
import { Field } from '../../../../components/DataForm';
import dayjs from 'dayjs';
import AdmissionViewer from './AdmissionViewer';

const AdmissionFields: GridColDef<Admission>[] = [
    { field: "admissionId", headerName: "ID" },
    { field: "description", headerName: "Description", flex: 1 },
    { field: "admissionDate", headerName: "Admission Date", width: 250 },
    { field: "dischargeDate", headerName: "Discharge Date", valueGetter: (v) => v ?? "Ongoing", width: 250 },
]

const PatientDetails = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [patient, setPatient] = useState<Patient>();
    const [admissions, setAdmissions] = useState<Admission[]>([]);
    const [loading, setLoading] = useState(true);

    const currentAdmission = admissions.find((a) => a.dischargeDate === null)

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
        let admissionRes = await API.findAdmissionsByPatient(Number.parseInt(id));

        setAdmissions(admissionRes.data);
        setPatient(patientRes.data);

        setLoading(false);
    });

    const refresh = () => {
        setLoading(true);
        loadPatient();
    }

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

    const FormFields = {
        admissionDate: Field.date("Admission Date", { minDate: dayjs() }),
        description: Field.text("Description"),
        appointmentId: Field.select("Appointment", {
            loader: async () => (await API.findAppointmentsByPatient(patient.personId!)).data,
            getInitial: (o: Admission) => o.appointment?.appointmentId,
            valueFor: (v) => v.appointmentId,
            labelFor: (v) => v.date!,
        }),
    }


    return <>
        <PageTitle>Patient Details</PageTitle>
        <Divider />

        <Box mt={4} mb={4}>
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

        {page === "admission" ? <AdmissionViewer admission={currentAdmission!} canEdit refresh={refresh} /> : null}


        {page === "history" ?
            <TablePage
                disableAdd={currentAdmission != null}
                name='Admissions'
                getId={(a) => a.admissionId!}
                formFields={FormFields}
                createHandler={(req) => API.createAdmission(req)}
                viewHandler={() => console.log}
                updateHandler={(id, data) => API.updateAdmission(id, data)}
                readHandler={async () => { return API.findAdmissionsByPatient(patient.personId!) }}
                columns={AdmissionFields}
            /> : null}

    </>
}

export default PatientDetails;