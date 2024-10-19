import { GridColDef } from "@mui/x-data-grid"
import { PageTitle } from "../../components/Logo"
import { Appointment } from "../../api"
import TablePage from "../../admin/components/TablePage"
import { API } from "../../config"

const AppointmentList = () => {

    const AppointmentFields: GridColDef<Appointment>[] = [
        { field: 'appointmentId', headerName: 'ID', width: 90 },
        { field: 'date', headerName: "Date", width: 200, flex: 1 },
        { field: 'time', headerName: "Time", width: 200, flex: 1 },
        { field: 'queueNo', headerName: "Queue No.", width: 200, flex: 1 },
        { field: 'status', headerName: "Gender", width: 90 },
    ];

    return (
        <>
            <PageTitle>Appointment List</PageTitle>
            <TablePage readHandler={() => API.getAllAppointments()} getId={(a) => a.appointmentId!} columns={AppointmentFields} name="Appointment" />
        </>
    )
}

export default AppointmentList