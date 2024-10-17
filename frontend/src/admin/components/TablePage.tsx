import { GridColDef, GridValidRowModel } from '@mui/x-data-grid'
import { useEffect, useState } from 'react'
import { useConfirm } from 'material-ui-confirm';
import { enqueueSnackbar } from 'notistack';
import { Box, Button, Divider, Paper, Stack, Typography } from '@mui/material';
import { PageSubtitle, PageTitle } from '../../shared/components/Logo';
import DataForm, { FieldData, Fields } from '../../shared/components/DataForm';
import StyledDataGrid from '../../shared/components/StyledDataGrid';
import { AxiosResponse } from 'axios';
import { Add } from '@mui/icons-material';
import { DNA } from 'react-loader-spinner';

function sleep(duration: number): Promise<void> {
    return new Promise<void>((resolve) => {
        setTimeout(() => {
            resolve();
        }, duration);
    });
}


interface TablePageProps<T extends GridValidRowModel, F extends Fields> {
    name: string
    title: string
    subtitle: string

    columns: GridColDef<T>[]
    formFields: F

    readHandler: (() => Promise<AxiosResponse<T[]>>)
    createHandler: ((data: FieldData<F>) => Promise<AxiosResponse<T>>)
    deleteHandler: ((id: number) => Promise<any>)
    updateHandler: ((id: number, updated: FieldData<F>) => Promise<AxiosResponse<T>>)
    getId: (data: T) => number

}



function TablePage<T extends GridValidRowModel, F extends Fields>(props: TablePageProps<T, F>) {
    const [rows, setRows] = useState<T[]>([]);
    const [editorShown, setEditorShown] = useState(false);
    const [selectedItem, setSelectedItem] = useState<T | null>(null);
    const [loading, setLoading] = useState(true);

    const confirm = useConfirm();

    const fetchRows = (async () => {
        setLoading(true)
        let response = await props.readHandler();
        setRows([...response.data]);
        setLoading(false);
    });

    // load data from server
    useEffect(() => {
        fetchRows()
    }, []);

    const onCreate = async (data: FieldData<F>) => {

        try {
            setLoading(true);
            await props.createHandler(data);

            // update the state
            await fetchRows();
            await sleep(500);

            enqueueSnackbar(`${props.name} created successfully`, { variant: "success" });

            closeEditor();
        } catch (e) {
            enqueueSnackbar(`Failed to Create ${props.name}`, { variant: "error" });
            console.error(e);
        }
    }

    const onUpdate = async (data: FieldData<F>) => {
        try {
            setLoading(true);

            await props.updateHandler(props.getId(selectedItem!), data)

            // update the state
            await fetchRows();
            await sleep(500);

            enqueueSnackbar(`${props.name} Updated Successfully`, { variant: "success" });

            // close editor
            closeEditor()
        } catch (e) {
            enqueueSnackbar(`Failed to Update ${props.name}`, { variant: "error" });
            console.error(e);
        }
    }

    const onEdit = async (id: number) => {
        // find the row for the given id.
        const row = rows.find((w) => props.getId(w) === id)!;;

        // display the editor
        setEditorShown(true);
        setSelectedItem(row);
    }


    const onDelete = async (id: number) => {
        const row = rows.find((w) => props.getId(w) === id)!;;

        await confirm({ title: `Permanently Delete ${props.name} ${id}?` });
        try {
            setLoading(true);
            await props.deleteHandler(props.getId(row))

            fetchRows();

            enqueueSnackbar(`${props.name} Deleted Successfully`, { variant: "success" });
        } catch (e) {
            enqueueSnackbar(`Failed to Delete ${props.name}`, { variant: "error" });
            console.error(e);
        }
    }

    const closeEditor = () => {
        setSelectedItem(null);
        setEditorShown(false);
    }



    return (
        <>
            <Box sx={{ pb: 2 }}>
                <PageTitle >{props.title}</PageTitle>
                <PageSubtitle>{props.subtitle}</PageSubtitle>
                <Divider />
            </Box>

            {editorShown ?
                <Paper elevation={2} sx={{ p: "12px" }}>
                    <PageTitle>{selectedItem == null ? `Create ${props.name}` : `Edit ${props.name} ${(selectedItem as any).name ?? ""}`}</PageTitle>
                    <DataForm
                        sx={{ my: 4 }}
                        fields={props.formFields}
                        canCancel
                        onCancel={closeEditor}
                        onSubmit={selectedItem == null ? onCreate : onUpdate}
                        initial={selectedItem ?? undefined}
                        submitText={selectedItem == null ? "Create" : "Update"} />
                </Paper>
                :
                <>
                    {loading ?
                        <Stack direction="column" alignItems="center" sx={{ width: "100%" }}>
                            <DNA
                                visible={true}
                                height="30vh"
                                width="30vw"
                                ariaLabel="dna-loading"
                                wrapperStyle={{}}
                                wrapperClass="dna-wrapper"
                            />
                            <Typography sx={{ fontSize: "1.4em" }}>Loading...</Typography>
                        </Stack> :
                        <>
                            <Stack direction="row" py={1}>
                                <Box sx={{ flexGrow: 1 }} />
                                <Button variant="outlined" startIcon={<Add />} sx={{
                                    backgroundColor: "#DB5356",
                                    color: "white",
                                    border: 'none'
                                }} onClick={() => setEditorShown(true)}>Add {props.name}</Button>
                            </Stack>
                            <StyledDataGrid
                                columns={props.columns}
                                rows={rows}
                                getRowId={props.getId}
                                onDelete={onDelete}
                                onEdit={onEdit}
                                loading={loading} />
                        </>
                    }

                </>
            }
        </>
    )
}

export default TablePage