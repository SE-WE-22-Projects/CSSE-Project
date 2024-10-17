import { GridColDef } from '@mui/x-data-grid'
import { Patient } from '../../../api';
import { Field } from '../../../shared/components/DataForm';
import TablePage from '../../components/TablePage';
import { API } from '../../../config';


const GridFields: GridColDef<Patient>[] = [
    { field: 'personId', headerName: 'ID', width: 90 },
    { field: 'name', headerName: "Name", width: 200, flex: 1 },
    { field: 'dob', headerName: "Date of Birth", width: 200, flex: 1 },
    { field: 'gender', headerName: "Gender", width: 90 },
]

const FormFields = {
    name: Field.text("Name", { minLength: 1, maxLength: 50 }),
    dob: Field.date("Date of birth"),
    gender: Field.select("Gender", { values: [{ label: "Male", value: "male" }, { label: "Female", value: "female" }] }),
    address: Field.text("Address", { textArea: true }),
    email: Field.email("Email"),
    phoneNo: Field.phone("Phone"),
    departmentId: Field.number("Department"),
    ward: Field.number("Ward"),
    speciality: Field.text("Speciality"),
    regNo: Field.text("Registration No")
}


const DoctorPage = () => {
    return <TablePage
        name="Doctor" title='Doctor Management'
        subtitle='Manager Doctor Details'
        columns={GridFields}
        formFields={FormFields}
        getId={(w) => w.personId!}
        readHandler={() => API.getAllDoctors()}
        createHandler={(req) => API.createDoctor(req)}
        deleteHandler={(id) => API.deleteDoctor(id)}
        updateHandler={(id, req) => API.updateDoctor(id, req)}
    />
}

export default DoctorPage