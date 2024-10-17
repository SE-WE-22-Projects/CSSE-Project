import { GridColDef } from '@mui/x-data-grid'
import { Department } from '../../../api';
import { Field } from '../../../shared/components/DataForm';
import TablePage from '../../components/TablePage';
import { API } from '../../../config';


const GridFields: GridColDef<Department>[] = [
    { field: 'deptId', headerName: 'ID', width: 90 },
    { field: 'name', headerName: "Department Name", width: 200, flex: 1 },
]

const FormFields = {
    name: Field.text("Department Name", { minLength: 1, maxLength: 50 }),
}


const DepartmentPage = () => {
    return <TablePage
        name="Department" title='Department Management'
        subtitle='Manage all departments in the hospital'
        columns={GridFields}
        formFields={FormFields}
        getId={(w) => w.departmentId!}
        readHandler={() => API.getAllDepartments()}
        createHandler={(req) => API.createDepartment(req)}
        deleteHandler={(id) => API.deleteDepartment(id)}
        updateHandler={(id, req) => API.updateDepartment(id, req)}
    />
}

export default DepartmentPage