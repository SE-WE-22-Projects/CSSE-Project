import { GridColDef } from '@mui/x-data-grid'
import { Ward } from '../../../api';
import { Field } from '../../../components/DataForm';
import TablePage from '../../components/TablePage';
import { API } from '../../../config';


const GridFields: GridColDef<Ward>[] = [
    { field: 'wardId', headerName: 'ID', width: 90 },
    { field: 'name', headerName: "Ward Name", width: 200, flex: 1 },
    { field: 'location', headerName: "Location", width: 200, flex: 1 },
    { field: 'capacity', headerName: "Capacity", width: 90 },
]

const FormFields = {
    name: Field.text("Ward Name", { minLength: 1, maxLength: 50 }),
    location: Field.text("Location", { minLength: 1, maxLength: 50 }),
    capacity: Field.number("Capacity")
}


const WardPage = () => {
    return <TablePage
        name="Ward" title='Ward Management'
        subtitle='Manage all wards in the hospital'
        columns={GridFields}
        formFields={FormFields}
        getId={(w) => w.wardId!}
        readHandler={() => API.getAllWards()}
        createHandler={(req) => API.createWard(req)}
        deleteHandler={(id) => API.deleteWard(id)}
        updateHandler={(id, req) => API.updateWard(id, req)}
        searcher={(row, query) => query.test(row.name ?? "")}
    />
}

export default WardPage