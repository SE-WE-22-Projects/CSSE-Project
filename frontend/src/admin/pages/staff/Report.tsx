import { GridColDef } from '@mui/x-data-grid'
import { MedicalService, Report, ReportStatusEnum } from '../../../api'
import TablePage from '../../components/TablePage'
import { API } from '../../../config'
import { Field, OptionType } from '../../../components/DataForm'

const ReportFields: GridColDef<Report>[] = [
    { field: "reportId", headerName: "ID" },
    { field: "medicalService", headerName: "Service Name", valueGetter: (v: MedicalService) => v?.name ?? "", flex: 1 },
    { field: "result", headerName: "Result", flex: 1 },
    { field: "status", headerName: "Status" },
]


const ReportForm = {
    result: Field.text("Result", { textArea: true }),
    status: Field.select("Status", {
        values: [
            { label: "Pending", value: ReportStatusEnum.Pending },
            { label: "Complete", value: ReportStatusEnum.Completed },
        ] as OptionType<ReportStatusEnum>[]
    }
    )
}

export const ReportPage = () => {
    return (
        <TablePage
            name="Reports"
            getId={(r) => r.reportId!}
            columns={ReportFields}
            readHandler={() => API.getAllReports()}
            formFields={ReportForm}
            updateHandler={(id, req, c) => API.updateReport(id, { ...c, ...req, })}
        />
    )
}
