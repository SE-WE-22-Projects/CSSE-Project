import { GridColDef } from '@mui/x-data-grid'
import { Admission, MedicalService, Report } from '../../../../api'
import TablePage from '../../../components/TablePage'
import { API } from '../../../../config'
import DataForm, { Field, FieldData } from '../../../../components/DataForm'
import { Box, Button, Divider, List, ListItem, ListItemText } from '@mui/material'
import { PageTitle } from '../../../../components/Logo'
import { useState } from 'react'

const ReportFields: GridColDef<Report>[] = [
    { field: "reportId", headerName: "ID" },
    { field: "medicalService", headerName: "Service Name", valueGetter: (v: MedicalService) => v?.name ?? "", flex: 1 },
    { field: "result", headerName: "Result", flex: 1 },
    { field: "status", headerName: "Status" },
]

const AdmissionForm = {
    serviceId: Field.select("Service", {
        loader: async () => (await API.getAllServices()).data,
        valueFor: (v) => v.serviceId,
        labelFor: (v) => v.name!,
        getInitial: (o: Report) => o.medicalService?.serviceId
    }),
}

const DiagnosisForm = {
    diagnosis: Field.text("Diagnosis", { textArea: true }),
    prescription: Field.text("Prescription", { textArea: true })
}

const AdmissionViewer = ({ admission, canEdit, refresh }: { admission: Admission, canEdit: boolean, refresh: () => any }) => {

    const [editing, setEditing] = useState(false);

    const updateDiagnosis = async (data: FieldData<typeof DiagnosisForm>) => {
        await API.updateDiagnosis(admission.diagnosis?.diagnosisId!, { ...data, admissionId: admission.admissionId });
        setEditing(false);
        refresh();
    }

    return (
        <>
            <PageTitle>Diagnosis</PageTitle>
            <Divider />
            {editing ? <DataForm fields={DiagnosisForm} submitText='Update' onSubmit={updateDiagnosis} initial={admission.diagnosis} /> :
                <>
                    <List dense >
                        <ListItem>
                            <ListItemText primary="Diagnosis" secondary={admission.diagnosis?.diagnosis ?? "Unknown"} />
                        </ListItem>
                        <ListItem>
                            <ListItemText primary="Prescription" secondary={admission.diagnosis?.prescription ?? "-"} />
                        </ListItem>
                    </List>
                    <Box>
                        <Button variant='contained' onClick={() => setEditing(true)}>Edit</Button>
                    </Box>
                </>
            }
            <TablePage
                title='Reports'
                name="Report Request"
                columns={ReportFields}
                formFields={AdmissionForm}
                readHandler={() => API.getAllReports()}
                getId={(v) => v.reportId!}
                createHandler={canEdit ? (req) => API.createReport({ ...req, admissionId: admission.admissionId, status: "PENDING" }) : undefined}
            />
        </>
    )
}

export default AdmissionViewer