import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom'
import { API } from '../../config';
import { Charge } from '../../api';
import { Box, Button, IconButton, Paper } from '@mui/material';
import StyledDataGrid from '../../components/StyledDataGrid';
import { GridColDef } from '@mui/x-data-grid';
import { PageTitle } from '../../components/Logo';
import { ArrowBack, Delete } from '@mui/icons-material';

const ChargeFields: GridColDef<Charge>[] = [
    { field: "chargeId", headerName: "ID" },
    { field: "amount", headerName: "Amount" },
]

const BillView = () => {
    const { id } = useParams();
    const [charges, setCharges] = useState<Charge[]>([]);
    const navigate = useNavigate();

    useEffect(() => {
        (async () => {
            const resp = await API.getAllChargeByPatient(id as any);
            setCharges(resp.data);
        })()
    }, [id])

    return (
        <>
            <Box marginTop={10} mx={10} alignItems={"center"}>

                <Paper sx={{ px: 8, py: 8 }}>
                    <Box justifyContent={"center"}>
                        <Button variant="outlined" startIcon={<ArrowBack />} onClick={()=>navigate("/appointment/list")}>
                            Back
                        </Button>

                        <PageTitle>Bill details for appointment on: {charges[0]?.appointment?.date}</PageTitle>

                        <StyledDataGrid columns={ChargeFields} rows={charges} getRowId={(r) => r.chargeId!} />

                    </Box>
                </Paper>
            </Box>
        </>
    )
}

export default BillView