import { useEffect, useState } from 'react'
import AppAppBar from '../components/landing/AppAppBar'
import { Box, Button } from '@mui/material'
import { PageTitle } from '../../components/Logo'
import AppointmentForm from './AppointmentForm'
import { Doctor, Schedule } from '../../api'
import { API } from '../../config'
import StyledDataGrid from '../../components/StyledDataGrid'
import { GridColDef } from '@mui/x-data-grid'
import { enqueueSnackbar } from 'notistack'


const Appointment = () => {
  const [doctorList, setDoctorList] = useState<Doctor[]>([]);
  const [scheduleList, setScheduleList] = useState<Schedule[]>([]);

  const [showDoctorList, setShowDoctorList] = useState<boolean>(true);
  const [hideAppointmentForm, setHideAppointmentForm] = useState<boolean>(true);

  const [selectedSchedule, setSelectedSchedule] = useState<Schedule | undefined>();

  const fetchDoctorList = async () => {
    const response = await API.getAllDoctors();
    setDoctorList(response.data);
  }
  const fetchScheduleList = async (id: number) => {
    const response = await API.getSchduleByDoctor(id);
    setScheduleList(response.data);
  }

  const DoctorFields: GridColDef<Doctor>[] = [
    { field: 'personId', headerName: 'ID', width: 90 },
    { field: 'name', headerName: "Name", width: 200, flex: 1 },
    { field: 'dob', headerName: "Date of Birth", width: 200, flex: 1 },
    { field: 'gender', headerName: "Gender", width: 90 },
  ]

  const ScheduleFields: GridColDef<Schedule>[] = [
    { field: 'scheduleId', headerName: 'ID', width: 90 },
    { field: 'name', headerName: 'Name', width: 90 },
    { field: 'description', headerName: 'Description', width: 90, flex: 1 },
    { field: 'day', headerName: 'Day', width: 90 },
    { field: 'startTime', headerName: 'Start time', width: 90 },
    { field: 'endTime', headerName: 'End time', width: 90 }
  ]

  const viewSchedule = (id: number) => {
    fetchScheduleList(id);
    setShowDoctorList(false);
  }
  const viewAppointmentForm = (id: number) => {
    setSelectedSchedule(scheduleList.find((schedule: Schedule) => schedule.scheduleId === id))
    setHideAppointmentForm(false);
  }

  useEffect(() => {
    fetchDoctorList();
  }, []);

  const handleSubmit = async (data: { date: string }) => {
    try {
      await API.createAppointmentByPatient({ ...data, scheduleId: selectedSchedule?.scheduleId });
      setShowDoctorList(true);
      setHideAppointmentForm(true);
      enqueueSnackbar("Appointment Created Successfully");
    }
    catch (e) {
      enqueueSnackbar("Failed to create appointment");
    }
  }

  const reset = () => {
    setSelectedSchedule(undefined);
    setShowDoctorList(true);
    setHideAppointmentForm(true);
  }
  return (
    <>
      <AppAppBar />
      <Box marginTop={20} mx={10} alignItems={"center"}>
        <Box justifyContent={"center"}>
          <PageTitle textAlign="center">Create Appointment</PageTitle>
          {
            hideAppointmentForm ?
              showDoctorList ?
                <>
                  <PageTitle>Doctor List</PageTitle>
                  <StyledDataGrid
                    rows={doctorList}
                    columns={DoctorFields}
                    onView={viewSchedule}
                    getRowId={(w) => w.personId!}
                  />
                </>
                :
                <>
                  <PageTitle>Schedule List</PageTitle>
                  <StyledDataGrid
                    rows={scheduleList}
                    columns={ScheduleFields}
                    onView={viewAppointmentForm}
                    getRowId={(w) => w.scheduleId!}
                  />
                </>
              :
              <AppointmentForm handleSubmit={handleSubmit} selectedSchedule={selectedSchedule} />
          }
          <Box my={5} display={"flex"}>
            <Box flexGrow={1}></Box>
            <Button variant="contained" onClick={reset}>Reset</Button>
          </Box>
        </Box>
      </Box>
    </>
  )
}

export default Appointment