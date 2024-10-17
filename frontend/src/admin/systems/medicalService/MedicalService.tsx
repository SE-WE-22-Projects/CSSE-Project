import { GridColDef } from '@mui/x-data-grid'
import { MedicalService } from '../../../api';
import { Field } from '../../../shared/components/DataForm';
import TablePage from '../../components/TablePage';
import { API } from '../../../config';


const GridFields: GridColDef<MedicalService>[] = [
    { field: 'serviceId', headerName: 'ID', width: 90 },
    { field: 'name', headerName: "Service Name", width: 200, flex: 1 },
    { field: 'description', headerName: "Description", width: 200, flex: 2 },
    { field: 'category', headerName: "Category", width: 100, flex: 1 },
    { field: 'price', headerName: "Price", width: 90 },
]

const FormFields = {
    name: Field.text("Service Name", { minLength: 1, maxLength: 50 }),
    description: Field.text("Description", { minLength: 1, maxLength: 500, textArea: true }),
    category: Field.text("Category"),
    price: Field.number("Price", { allowFloat: true })
}


const MedicalServicePage = () => {
    return <TablePage
        name="Service" title='Medical Service Management'
        subtitle='Manage all medical services in the hospital'
        columns={GridFields}
        formFields={FormFields}
        getId={(w) => w.serviceId!}
        readHandler={() => API.getAllServices()}
        createHandler={(req) => API.createService(req)}
        deleteHandler={(id) => API.deleteService(id)}
        updateHandler={(id, req) => API.updateService(id, req)}
    />
}

export default MedicalServicePage;