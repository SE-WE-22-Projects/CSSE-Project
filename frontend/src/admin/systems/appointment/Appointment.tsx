import { GridColDef } from '@mui/x-data-grid'
import { Appointment, AppointmentStatusEnum, Patient, Schedule } from '../../../api';
import { Field } from '../../../components/DataForm';
import TablePage from '../../components/TablePage';
import { API } from '../../../config';

const GridFields: GridColDef<Appointment>[] = [
    { field: 'appointmentId', headerName: 'ID', width: 90 },
    { field: 'patient', headerName: "Patient", flex: 1, valueGetter: (v: Patient) => v?.name },
    { field: 'schedule', headerName: "Schedule", flex: 1, valueGetter: (v: Schedule) => v?.name },
    { field: 'date', headerName: "Date", flex: 1 },
    { field: 'time', headerName: "Time", flex: 1 },
    { field: 'queueNo', headerName: "Queue No", flex: 1 },
    { field: 'status', headerName: "Status", },
]

const FormFields = {
    date: Field.date("Date"),
    time: Field.time("Time"),
    queueNo: Field.number("Queue No"),
    status: Field.select("Status", {
        values: [
            { label: "Pending", value: AppointmentStatusEnum.Pending }],
    }),
    patientId: Field.select("Patient", {
        loader: async () => (await API.getAllPatients()).data,
        getInitial: (v: Appointment) => v.patient?.personId,
        labelFor: (v: Patient) => v.name!,
        valueFor: (v: Patient) => v.personId
    }),
    scheduleId: Field.select("Schedule", {
        loader: async () => (await API.getAllSchedules()).data,
        getInitial: (v: Appointment) => v.schedule?.scheduleId,
        labelFor: (v: Schedule) => v.name!,
        valueFor: (v: Schedule) => v.scheduleId
    })
}


const AppointmentPage = () => {
    return <TablePage
        name="Appointment" title='Appointment Management'
        subtitle='Manage appointments in the hospital'
        columns={GridFields}
        formFields={FormFields}
        getId={(w) => w.appointmentId!}
        readHandler={() => API.getAllAppointments()}
        createHandler={(req) => API.createAppointment(req)}
        deleteHandler={(id) => API.deleteAppointment(id)}
        updateHandler={(id, req) => API.updateAppointment(id, req)}
        searcher={(row, query) => query.test(row.patient?.name ?? "") || query.test(row.schedule?.doctor?.name ?? "")}
    />
}

export default AppointmentPage