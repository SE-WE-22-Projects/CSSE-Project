import { Patient } from '../../../../api'
import { GridColDef } from '@mui/x-data-grid'
import TablePage from '../../../components/TablePage'
import { API } from '../../../../config'
import { useNavigate } from 'react-router-dom'

const GridFields: GridColDef<Patient>[] = [
    { field: 'personId', headerName: 'ID', width: 90 },
    { field: 'name', headerName: "Name", width: 200, flex: 1 },
    { field: 'dob', headerName: "Date of Birth", width: 200, flex: 1 },
    { field: 'gender', headerName: "Gender", width: 90 },
]

const PatientList = () => {
    const navigate = useNavigate();

    return <TablePage
        name="Patient" title='Patient Details'
        subtitle='View Details of Registered Patients'
        columns={GridFields}
        getId={(w) => w.personId!}
        readHandler={() => API.getAllPatients()}
        viewHandler={(row) => navigate(`./${row.personId}`)}
        searcher={(row, query) => query.test(row.name ?? "")}
    />
}

export default PatientList