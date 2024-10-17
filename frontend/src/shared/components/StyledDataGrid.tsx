import { Delete, Edit, Visibility } from "@mui/icons-material";
import { Paper, Skeleton } from "@mui/material";
import { DataGrid, DataGridProps, GridActionsCellItem, GridColDef, GridValidRowModel } from "@mui/x-data-grid";
import { ReactElement } from "react";

interface ActionButtonProps {
    onView?: (id: any) => void,
    onEdit?: (id: any) => void,
    onDelete?: (id: any) => void,
}
function StyledDataGrid<R extends GridValidRowModel,>(props: DataGridProps<R> & ActionButtonProps) {

    if (props.onView || props.onEdit || props.onDelete) {
        let actionField: GridColDef<R> = {
            field: "actions", type: "actions", getActions: ({ id }) => {
                let actions: ReactElement[] = [];

                if (props.onView) {
                    actions.push(<GridActionsCellItem
                        icon={<Visibility />}
                        label="View"
                        className="textPrimary"
                        onClick={() => props.onView!(id)}
                        color="inherit" />)
                }

                if (props.onEdit) {
                    actions.push(<GridActionsCellItem
                        icon={<Edit />}
                        label="Edit"
                        className="textPrimary"
                        onClick={() => props.onEdit!(id)}
                        color="inherit" />)
                }

                if (props.onDelete) {
                    actions.push(<GridActionsCellItem
                        icon={<Delete />}
                        label="Delete"
                        onClick={() => { props.onDelete ? props.onDelete(id) : null }}
                        color="inherit" />)
                }

                return actions;
            },
            sortable: false
        };

        props = { ...props, columns: [...props.columns, actionField] }
    }


    return <Paper elevation={2}> <DataGrid {...props} slots={{
        loadingOverlay: () => <Skeleton />,
    }}

        rows={props.rows}
        initialState={{
            sorting: {
                sortModel: [{ field: props.columns[0].field, sort: 'asc' }],
            },
        }}
    />
    </Paper>
}

export default StyledDataGrid;