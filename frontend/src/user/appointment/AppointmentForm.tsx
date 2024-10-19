import Paper from '@mui/material/Paper'
import React from 'react'
import DataForm, { Field, FieldData } from '../../components/DataForm';
import dayjs from 'dayjs';
import { Schedule, ScheduleRequestDayEnum } from '../../api';

const getWeekDayNumber = (day : ScheduleRequestDayEnum)=>{
    switch(day) {
        case 'SUNDAY': return 0;
        case 'MONDAY': return 1;
        case 'TUESDAY': return 2
        case 'WEDNESDAY': return 3;
        case 'THURSDAY': return 4;
        case 'FRIDAY': return 5;
        case 'SATURDAY': return 6;
    }

}

const AppointmentForm = ({ handleSubmit, selectedSchedule }: { handleSubmit: (d: {date: string})=> Promise<void>, selectedSchedule: Schedule | undefined }) => {
    
    const day = getWeekDayNumber(selectedSchedule?.day!);
    const appointmentFields = {
        date : Field.date("Appointment Date", {minDate: dayjs(), disableDate: (date)=> date.day() != day})
    }
    
    return (
        <>
            <div>Appointment Form</div>
            <Paper>
                <DataForm onSubmit={handleSubmit} fields={appointmentFields} />
            </Paper>
        </>
    );
}

export default AppointmentForm