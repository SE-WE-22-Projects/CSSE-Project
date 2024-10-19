import { useEffect, useState } from 'react'
import AppAppBar from '../components/landing/AppAppBar'
import { Box } from '@mui/material'
import { PageTitle } from '../../components/Logo'
import AppointmentForm from './AppointmentForm'
import { AppointmentRequest, Doctor, Schedule } from '../../api'
import { API } from '../../config'
import StyledDataGrid from '../../components/StyledDataGrid'
import { GridColDef } from '@mui/x-data-grid'


const Appointment = () => {
  const [doctorList, setDoctorList] = useState<Doctor[]>([]);
  const [scheduleList, setScheduleList] = useState<Schedule[]>([]);

  const [showDoctorList, setShowDoctorList] = useState<boolean>(false);

  const fetchDoctorList = async () => {
    const response = await API.getAllDoctors();
    setDoctorList(response.data);
  }
  const fetchScheduleList = (id: number) => {
    const response = await API.getSc
  }

  const DoctorFields: GridColDef<Doctor>[] = [
    { field: 'personId', headerName: 'ID', width: 90 },
    { field: 'name', headerName: "Name", width: 200, flex: 1 },
    { field: 'dob', headerName: "Date of Birth", width: 200, flex: 1 },
    { field: 'gender', headerName: "Gender", width: 90 },
  ]

  const viewSchedule = (id: number) => {
    console.log(id);
  }

  useEffect(() => {
    fetchDoctorList();
  }, []);

  const createAppointemt = (data: AppointmentRequest) => {

  }
  return (
    <>
      <AppAppBar />
      <Box marginTop={20} mx={10} alignItems={"center"}>
        <Box>
          <PageTitle>Appointment</PageTitle>
          {showDoctorList ?
            <StyledDataGrid
              rows={doctorList}
              columns={DoctorFields}
              onView={viewSchedule}
              getRowId={(w) => w.personId!}
            />
            :
            <></>
          }

          <AppointmentForm />
        </Box>
      </Box>
    </>
  )
}

export default Appointment