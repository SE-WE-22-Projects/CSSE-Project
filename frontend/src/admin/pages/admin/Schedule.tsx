import { GridColDef } from '@mui/x-data-grid'
import { Doctor, Schedule, ScheduleRequestDayEnum } from '../../../api';
import { Field, OptionType } from '../../../components/DataForm';
import TablePage from '../../components/TablePage';
import { API } from '../../../config';


const GridFields: GridColDef<Schedule>[] = [
    { field: 'scheduleId', headerName: 'ID', width: 90 },
    { field: 'name', headerName: "Schedule Name", width: 200, flex: 1 },
    { field: 'doctor', headerName: "Doctor", valueGetter: (e: Doctor) => e?.name, width: 200, flex: 1 },
    { field: 'description', headerName: "Description", width: 200, flex: 1 },
    { field: 'startTime', headerName: "Start Time", width: 200, flex: 1 },
    { field: 'endTime', headerName: "End Time", width: 200, flex: 1 },
    { field: 'day', headerName: "Day", width: 90 },
]

const FormFields = {
    name: Field.text("Schedule Name", { minLength: 1, maxLength: 50 }),
    description: Field.text("Description", { minLength: 1, maxLength: 50, textArea: true }),
    startTime: Field.time("Start Time"),
    endTime: Field.time("End time"),
    day: Field.select("Day Of the Week", {
        values: [
            { label: "Sunday", value: ScheduleRequestDayEnum.Sunday },
            { label: "Monday", value: ScheduleRequestDayEnum.Monday },
            { label: "Tuesday", value: ScheduleRequestDayEnum.Tuesday },
            { label: "Wednesday", value: ScheduleRequestDayEnum.Wednesday },
            { label: "Thursday", value: ScheduleRequestDayEnum.Thursday },
            { label: "Friday", value: ScheduleRequestDayEnum.Friday },
            { label: "Saturday", value: ScheduleRequestDayEnum.Saturday },
        ] as OptionType<ScheduleRequestDayEnum>[]
    }),
    doctorId: Field.select("Doctor", {
        loader: async () => (await API.getAllDoctors()).data,
        valueFor: (v) => v.personId,
        labelFor: (v) => v.name!,
        getInitial: (o: Schedule) => o.doctor?.personId
    }),
}


const SchedulePage = () => {
    return <TablePage
        name="Schedule" title='Schedule Management'
        subtitle='Manage all schedules in the hospital'
        columns={GridFields}
        formFields={FormFields}
        getId={(w) => w.scheduleId!}
        readHandler={() => API.getAllSchedules()}
        createHandler={(req) => API.createSchedule(req as any)}
        deleteHandler={(id) => API.deleteSchedule(id)}
        updateHandler={(id, req) => API.updateSchedule(id, req as any)}
        searcher={(row, query) => query.test(row.name ?? "")}
    />
}

export default SchedulePage