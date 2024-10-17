import { GridColDef } from '@mui/x-data-grid'
import { useEffect, useState } from 'react'
import { API } from '../../App';
import { Ward, WardRequest } from '../../api';
import DataForm, { Field } from '../../shared/components/DataForm';
import { PageTitle, PageSubtitle } from '../../shared/components/Logo';
import { useConfirm } from 'material-ui-confirm';
import { enqueueSnackbar } from 'notistack';
import StyledDataGrid from '../../shared/components/StyledDataGrid';

function sleep(duration: number): Promise<void> {
    return new Promise<void>((resolve) => {
        setTimeout(() => {
            resolve();
        }, duration);
    });
}

const GridFields: GridColDef<Ward>[] = [
    { field: 'wardId', headerName: 'ID', width: 90 },
    { field: 'name', headerName: "Ward Name", width: 200 },
    { field: 'location', headerName: "Location", width: 200 },
    { field: 'capacity', headerName: "Capacity", width: 90 },
]

const FormFields = {
    name: Field.text("Ward Name", { minLength: 1, maxLength: 50 }),
    location: Field.text("Location", { minLength: 1, maxLength: 50 }),
    capacity: Field.number("Capacity")
}



const WardPage = () => {
    const [wards, setWards] = useState<Ward[]>([]);
    const [editorShown, setEditorShown] = useState(false);
    const [selectedWard, setSelectedWard] = useState<Ward | null>(null);

    const confirm = useConfirm();

    // load data from server
    useEffect(() => {
        (async () => {
            let response = await API.getAllWards()
            setWards(response.data);
        })()
    }, []);

    const createWard = async (data: WardRequest) => {
        await sleep(1000);

        try {
            let response = await API.createWard(data);
            setWards([...wards, response.data]);
            enqueueSnackbar("Ward created successfully", { variant: "success" });
        } catch (e) {
            enqueueSnackbar("Failed to create ward", { variant: "error" });
            console.error(e);
        }
    }

    const updateWard = async (data: WardRequest) => {
        await sleep(1000);
        try {
            let response = await API.updateWard(selectedWard?.wardId!, data);
            setWards([...wards, response.data]);
            enqueueSnackbar("Ward updated successfully", { variant: "success" });
        } catch (e) {
            enqueueSnackbar("Failed to update ward", { variant: "error" });
            console.error(e);
        }
    }

    const editWard = async (id: number) => {
        const ward = wards.find((w) => w.wardId === id)!;

        setEditorShown(true);
        setSelectedWard(ward);
    }


    const deleteWard = async (id: number) => {
        const ward = wards.find((w) => w.wardId === id)!;

        await confirm({ title: `Permanently delete ward ${ward.name}?` });
        try {
            await API.deleteWard(ward.wardId!);
            setWards(wards.filter(i => i.wardId != ward.wardId))
            enqueueSnackbar("Ward deleted successfully", { variant: "success" });
        } catch (e) {
            enqueueSnackbar("Failed to delete ward", { variant: "error" });
            console.error(e);
        }
    }



    return (
        <>
            <PageTitle >Ward Management</PageTitle>
            <PageSubtitle>Manage all wards in the hospital</PageSubtitle>
            {editorShown ?
                <DataForm fields={FormFields}
                    onSubmit={selectedWard == null ? createWard : updateWard}
                    initial={selectedWard ?? undefined}
                    submitText={selectedWard == null ? "Create" : "Update"} /> :
                <StyledDataGrid columns={GridFields} rows={wards} getRowId={(v) => v.wardId!} onDelete={deleteWard} onEdit={editWard} />
            }
        </>
    )
}

export default WardPage