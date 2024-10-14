import { useEffect, useState } from "react";
import { Appointment } from "../../api";
import { API } from "../../main";
import { DataGrid, GridColDef } from "@mui/x-data-grid";
import { Button } from "@mui/material";

const columns: GridColDef[] = [
    { field: 'appointmentId', headerName: 'ID', width: 150 },
    { field: 'date', headerName: 'Date', width: 150 },
];

const Page1 = () => {
    const [data, setData] = useState<Appointment[]>([]);

    useEffect(() => {
        API.getAllAppointments().then((v) => {
            setData(v.data);
        });
    }, [])

    const createTest = () => {
        API.createAppointment({ patientId: 1, date: "2024-01-12", time: "12:24", queueNo: 1 })
    }

    return <>
        <Button onClick={createTest}>Create</Button>
        <DataGrid rows={data} columns={columns} getRowId={(r) => r.appointmentId} />
    </>
}

export default Page1;