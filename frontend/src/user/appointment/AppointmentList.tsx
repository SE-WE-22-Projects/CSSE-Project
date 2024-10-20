import { GridColDef } from "@mui/x-data-grid"
import { PageTitle } from "../../components/Logo"
import { Appointment } from "../../api"
import TablePage from "../../admin/components/TablePage"
import { API } from "../../config"
import { Box, Paper } from "@mui/material"

const AppointmentList = () => {

    const AppointmentFields: GridColDef<Appointment>[] = [
        { field: 'appointmentId', headerName: 'ID', width: 90 },
        { field: 'date', headerName: "Date", width: 200, flex: 1 },
        { field: 'time', headerName: "Time", width: 200, flex: 1 },
        { field: 'queueNo', headerName: "Queue No.", width: 200, flex: 1 },
        { field: 'status', headerName: "Status", width: 90 },
    ];

    return (
        <>
            <Box marginTop={10} mx={10} alignItems={"center"}>
                <Paper sx={{ px: 8, py: 8 }}>
                    <Box justifyContent={"center"}></Box>
                    <PageTitle>Appointment List</PageTitle>
                    <TablePage readHandler={() => API.getAllAppointments()} getId={(a) => a.appointmentId!} columns={AppointmentFields} name="Appointment" />
                </Paper>
            </Box>
        </>
    )
}

export default AppointmentList