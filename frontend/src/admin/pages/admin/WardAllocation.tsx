import { GridColDef } from '@mui/x-data-grid'
import { Doctor, Ward } from '../../../api';
import { Field } from '../../../components/DataForm';
import TablePage from '../../components/TablePage';
import { API } from '../../../config';


const GridFields: GridColDef<Doctor>[] = [
    { field: 'personId', headerName: 'ID', width: 90 },
    { field: 'name', headerName: "Name", width: 200, flex: 1 },
    { field: 'ward', headerName: "Ward", valueGetter: (v: Ward) => v?.name ?? "Unassigned" }
]

const FormFields = {
    wardId: Field.select("Ward", {
        loader: async () => (await API.getAllWards()).data,
        getInitial: (o: Doctor) => o.ward?.wardId,
        valueFor: (v) => v.wardId,
        labelFor: (v) => v.name!
    }),
}


const WardAllocation = () => {
    return <TablePage
        name="Ward Allocation" title='Doctor Ward Allocation'
        subtitle='Allocate ward to doctors'
        columns={GridFields}
        formFields={FormFields}
        getId={(w) => w.personId!}
        readHandler={() => API.getAllDoctors()}
        updateHandler={(id, req) => API.allocateWard(id, req)}
        searcher={(row, query) => query.test(row.name ?? "")}
    />
}

export default WardAllocation