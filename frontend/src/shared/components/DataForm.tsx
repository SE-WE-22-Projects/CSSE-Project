import { Autocomplete, Box, Button, CircularProgress, FormControl, FormHelperText, InputLabel, Stack, StackOwnProps, TextareaAutosize, TextField, TextFieldVariants } from '@mui/material'
import { DatePicker, DateTimePicker, TimePicker } from '@mui/x-date-pickers'
import dayjs, { Dayjs } from 'dayjs'
import { useEffect, useState } from 'react'

const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
const numberRegex = /^[+-]?\d+(\.\d+)?$/;
const phoneRegex = /\d{10}/;

enum FieldType {
    Text,
    Number,
    Email,
    Phone,
    Date,
    Time,
    DateTime,
    Select
}

interface FieldBasic<T> {

    label: string,
    helper?: string,
    type: FieldType,

    /**
     * Marks the field as not required.
     */
    notRequired?: boolean,

    /**
     * Validates the field.
     * @returns a string if the field is invalid. Null or undefined otherwise
     */
    validator?: (v?: string) => string | undefined | null

    /**
    * Internal property. DO NOT USE.
    */
    __internal?: T
}

interface FieldEmail extends FieldBasic<string> {
    type: FieldType.Email
}

interface FieldPhone extends FieldBasic<string> {
    type: FieldType.Phone
}

interface FieldDate extends FieldBasic<string> {
    type: FieldType.Date,
    maxDate?: Dayjs
    minDate?: Dayjs
}

interface FieldTime extends FieldBasic<string> {
    type: FieldType.Time,
    maxTime?: Dayjs
    minTime?: Dayjs
}

interface FieldDateTime extends FieldBasic<string> {
    type: FieldType.DateTime,
    maxDate?: Dayjs
    minDate?: Dayjs
}

interface FieldText extends FieldBasic<string> {
    type: FieldType.Text,
    minLength?: number
    maxLength?: number
    textArea?: boolean
}



interface FieldNumber extends FieldBasic<number> {
    type: FieldType.Number,
    allowNegative?: boolean
    allowFloat?: boolean
    min?: number
    max?: number
}

type OptionType<T> = { label: string, value: T }[] | T[]

interface FieldSelect<T> extends FieldBasic<T> {
    type: FieldType.Select,
    values: OptionType<T>

    /**
     * A function that returns the label for the given option
     * Ex: the name from a person object
     * @param o the option
     * @returns the label to display
     */
    labelFor?: (o: T) => string;

    /**
     * A function that should return the value for the given option
     * Ex: the personId from a person object
     * @param o the option
     * @returns the value for selecting this option
     */
    valueFor?: (o: T) => any;


    /**
     * Internal property. DO NOT USE.
     */
    options: { label: string, value: T }[]
}


interface FieldLoadSelect<T> extends FieldBasic<T> {
    type: FieldType.Select,

    /**
     * A function that returns the label for the given option
     * Ex: the name from a person object
     * @param o the option
     * @returns the label to display
     */
    labelFor?: (o: T) => string;

    /**
     * A function that should return the value for the given option
     * Ex: the personId from a person object
     * @param o the option
     * @returns the value for selecting this option
     */
    valueFor?: (o: T) => any;

    loader: () => Promise<OptionType<T>>


    /**
     * Internal property. DO NOT USE.
     */
    options: { label: string, value: T }[] | null
}

type AllFields = FieldText | FieldNumber | FieldEmail | FieldPhone | FieldDate | FieldTime | FieldDateTime | FieldSelect<any> | FieldLoadSelect<any>


type FieldProps<T extends AllFields> = Omit<Omit<Omit<T, 'type'>, 'internal'>, 'label'>;
type SelectFieldProps<T> = Omit<FieldProps<FieldLoadSelect<T>>, 'options'> | Omit<FieldProps<FieldSelect<T>>, 'options'>

export const Field = {
    text: (label: string, field?: FieldProps<FieldText>): FieldText => {
        return { ...field, label, type: FieldType.Text };
    },

    email: (label: string, field?: FieldProps<FieldEmail>): FieldEmail => {
        return { ...field, label, type: FieldType.Email };
    },

    phone: (label: string, field?: FieldProps<FieldPhone>): FieldPhone => {
        return { ...field, label, type: FieldType.Phone };
    },

    date: (label: string, field?: FieldProps<FieldDate>): FieldDate => {
        return { ...field, label, type: FieldType.Date };
    },

    time: (label: string, field?: FieldProps<FieldTime>): FieldTime => {
        return { ...field, label, type: FieldType.Time };
    },

    dateTime: (label: string, field?: FieldProps<FieldDateTime>): FieldDateTime => {
        return { ...field, label, type: FieldType.DateTime };
    },

    number: (label: string, field?: FieldProps<FieldNumber>): FieldNumber => {
        return { ...field, label, type: FieldType.Number }
    },

    select: function <T,>(label: string, field: SelectFieldProps<T>): FieldSelect<T> | FieldLoadSelect<T> {

        let newField: FieldSelect<T> | FieldLoadSelect<T>

        if ((field as any).loader !== undefined) {
            newField = { ...(field as FieldProps<FieldLoadSelect<T>>), label, options: null, type: FieldType.Select };
        } else {
            newField = { ...(field as FieldProps<FieldSelect<T>>), label, type: FieldType.Select, options: getOptions(field as FieldSelect<T>, true) }
        }

        return newField;
    }
}


interface FormProps<T extends Fields,> {
    fields: T,
    fullWidth?: boolean,
    initial?: Partial<FieldData<T>>,
    sx?: StackOwnProps["sx"],
    submitText?: string,
    onSubmit?: ((data: FieldData<T>) => any) | ((data: FieldData<T>) => Promise<any>),
    canCancel?: boolean,
    onCancel?: () => any,
}


export interface Fields {
    [key: string]: AllFields
}


export type FieldData<T extends Fields,> = {
    [K in keyof T]: NonNullable<T[K]['__internal']>
}

type FieldErrors<T extends Fields,> = {
    [K in keyof T]: string | undefined | null
}

const dateFormat = (typ: FieldType) => typ === FieldType.Date ? "YYYY-MM-DD" : typ === FieldType.Time ? "HH:mm:ss" : "YYYY-MM-DD HH:mm:ss";
const dateSeconds = (date: Dayjs) => (date.hour() * 60 + date.minute()) * 60 + date.second();

/**
 * Validates the given field. This returns a string explaining the validation error if one is found.
 * @param field the field being validated
 * @param value the current value of the field
 * @returns a string explaining the validation error if one is found. null or undefined otherwise.
 */
const validateField = (field: FieldBasic<any>, value: any): string | null | undefined => {
    console.log(`value: '${value}' ${typeof value}`);

    // check if the required field is given.
    if ((value === null || value === undefined || value === "")) {
        if (!field.notRequired)
            return field.label + " is required";

        // value is not set and field is not required.
        // skip validation.
        return;
    }

    if (field.type === FieldType.Text) {
        // text length validations.
        let length = (value as string).length;
        let minLength = (field as FieldText).minLength || 0;
        let maxLength = (field as FieldText).maxLength || Infinity;

        if (length < minLength) {
            return `${field.label} must be at least ${minLength} letters.`
        } else if (length > maxLength) {
            return `${field.label} must be less than ${maxLength} letters.`
        }

    } else if (field.type === FieldType.Email) {
        // email validation
        if (!emailRegex.test(value)) {
            return `Please enter a valid email address`;
        }

    } else if (field.type === FieldType.Phone) {
        // phone number validation
        if (!phoneRegex.test(value)) {
            return `Please enter a valid phone number`;
        }
    } else if (field.type === FieldType.Number) {

        // number validation
        let numField = field as FieldNumber;
        let parsed = typeof value === 'number' ? value : parseFloat(value);

        if (Number.isNaN(parsed) || !Number.isFinite(parsed)) {
            return 'Please enter a valid number';
        } else if (Math.abs(parsed) - Math.round(Math.abs(parsed)) != 0) {
            return 'Please enter an integer';
        } else if (!numField.allowNegative && parsed < 0) {
            return 'Please enter a positive number';
        } else if (parsed < (numField.min || 0)) {
            return `${field.label} must be larger than ${numField.min}`;
        } else if (parsed > (numField.max || Infinity)) {
            return `${field.label} must be less than ${numField.min}`
        }
    } else if (field.type === FieldType.DateTime || field.type === FieldType.Date) {
        let dateField = field as FieldDateTime | FieldDate;
        let format = dateFormat(field.type);
        let date = dayjs(value, format, true);

        if (!date.isValid()) return "Please select a valid date";
        else if (dateField.maxDate !== undefined && date.isAfter(dateField.maxDate)) {
            return `Please select a date before ${dateField.maxDate.format(format)}`
        } else if (dateField.minDate !== undefined && date.isBefore(dateField.minDate)) {
            return `Please select a date after ${dateField.minDate.format(format)}`
        }
    } else if (field.type === FieldType.Time) {
        let timeField = field as FieldTime;
        let format = dateFormat(field.type);
        let date = dayjs(value, format, true);

        if (!date.isValid()) return "Please select a valid time";
        else if (timeField.minTime && dateSeconds(date) < dateSeconds(timeField.minTime)) {
            return `Please select a time after ${timeField.minTime.format(format)}`
        } else if (timeField.maxTime && dateSeconds(date) > dateSeconds(timeField.maxTime)) {
            return `Please select a time before ${timeField.maxTime.format(format)}`
        }

    }


    // call custom validators if given.
    if (field.validator !== undefined) {
        return field.validator(value);
    }
}


function DataForm<T extends Fields,>(props: FormProps<T>) {
    const [data, setData] = useState<Partial<FieldData<T>>>(props.initial ?? {});
    const [errors, setErrors] = useState<Partial<FieldErrors<T>>>({});
    const [isSubmitting, setIsSubmitting] = useState(false);


    const validate = () => {
        let canSubmit = true;

        // validate all fields
        let newErrors = {} as any;
        Object.entries(props.fields).forEach((f) => {
            let error = validateField(f[1], data[f[0]]);
            newErrors[f[0]] = error;
            if (error !== null && error !== undefined) {
                canSubmit = false;
            }
        });
        setErrors(newErrors);

        return canSubmit;

    }

    useEffect(() => {
        if (props.initial === undefined) return;
        validate();
    }, [props.initial]);

    const submit = async () => {
        let canSubmit = validate();

        console.log(data);

        if (canSubmit && props.onSubmit !== undefined) {

            let maybePromise = props.onSubmit(data as FieldData<T>);
            if (maybePromise.then !== undefined) {
                setIsSubmitting(true);
                try {
                    await maybePromise
                } finally {
                    setIsSubmitting(false);
                }

            }
        }
    }


    return (
        <Stack spacing={2} sx={props.sx}>
            {Object.entries(props.fields).map((f, idx) => {
                let key = f[0];
                let field = f[1];

                const fieldProps = {
                    field: field,
                    value: data[key] as any,
                    error: errors[key],
                    setError: (v: string | undefined | null) => {
                        let newErrors = { ...errors };
                        // @ts-expect-error
                        newErrors[key] = v;
                        setErrors(newErrors);
                    },
                    onChange: (v: any) => {
                        let newData = { ...data }
                        // @ts-expect-error
                        newData[key] = v;
                        setData(newData);
                    },
                    disabled: isSubmitting,
                    fullWidth: props.fullWidth
                }

                if (field.type === FieldType.Date || field.type === FieldType.Time || field.type == FieldType.DateTime) {
                    return <DateInput key={idx} {...fieldProps} />
                } else if (field.type === FieldType.Number) {
                    return <NumberInput key={idx} {...fieldProps} />
                } else if (field.type === FieldType.Select) {
                    return <SelectInput key={idx} {...fieldProps} />
                } else if (field.type === FieldType.Text && (field as FieldText).textArea) {
                    return <TextAreaInput key={idx} {...fieldProps} />
                }

                return <StringInput key={idx} {...fieldProps} />
            }
            )}

            <Stack direction="row">
                {props.canCancel ? <Button variant="outlined" color={"error"} onClick={props.onCancel} disabled={isSubmitting}>Cancel</Button> : null}
                <Box flexGrow={1} />
                <Button variant="contained" onClick={submit} disabled={isSubmitting} endIcon={
                    isSubmitting ? <CircularProgress size={20} /> : null}>{props.submitText ?? "Submit"}</Button>
            </Stack>
        </Stack>
    )
}

type InputProps<T> = {
    value: T | undefined,
    onChange: (v: T | undefined) => any,
    error: string | null | undefined,
    setError: (error: string | null | undefined) => void,
    field: AllFields,
    disabled: boolean,
    fullWidth?: boolean
}

/**
 * An input that takes a string value.
 */
const StringInput = ({ value, onChange, field, fullWidth, error, setError, disabled }: InputProps<string>) => {
    const [hasFocus, setHasFocus] = useState(false);

    return <TextField
        label={field.label}
        variant="outlined"
        required={!field.notRequired}
        helperText={(!hasFocus && error) ? error : field.helper}
        error={!!error && !hasFocus}
        value={value ?? ""}
        onChange={(v) => onChange(v.target.value)}
        onFocus={() => setHasFocus(true)}
        onBlur={() => {
            setHasFocus(false);
            setError(validateField(field, value));
        }}
        fullWidth={fullWidth}
        disabled={disabled} />
}

/**
 * An text area input that takes a string value.
 */
const TextAreaInput = ({ value, onChange, field, fullWidth, error, setError, disabled }: InputProps<string>) => {
    const [hasFocus, setHasFocus] = useState(false);

    return <FormControl error={!!error && !hasFocus} variant='outlined' fullWidth={fullWidth}>
        <InputLabel>{field.label}</InputLabel>
        <TextareaAutosize
            required={!field.notRequired}
            value={value ?? ""}
            onChange={(v) => onChange(v.target.value)}
            onFocus={() => setHasFocus(true)}
            onBlur={() => {
                setHasFocus(false);
                setError(validateField(field, value));
            }}
            disabled={disabled} />
        <FormHelperText error={!!error && !hasFocus} id="my-helper-text">{(!hasFocus && error) ? error : field.helper}</FormHelperText>
    </FormControl>;



}

/**
 * An input field that takes a number as the input
 */
const NumberInput = ({ error, setError, value, onChange, field, fullWidth, disabled }: InputProps<number>) => {
    const [data, setData] = useState<string>();

    // sync the value with the state of data
    useEffect(() => {
        if (value === undefined || isNaN(value)) return;
        if (parseFloat(data ?? "invalid") != value) {
            setData(value.toString());
        }
    }, [[value]])

    return <StringInput value={data} onChange={(v) => {
        setData(data);

        // try parse the number and call the onChange handler with the updated state
        let num: number
        if (v === undefined || !numberRegex.test(v)) {
            num = NaN;
        } else {
            num = Number.parseFloat(v);
        }

        onChange(num);
    }} field={field} fullWidth={fullWidth} error={error} setError={setError} disabled={disabled} />
}

/**
 * An input field that handles date/time based selections.
 */
const DateInput = ({ value, onChange, field, fullWidth, setError, error, disabled }: InputProps<string>) => {
    const [hasFocus, setHasFocus] = useState(false);

    // select date format based on field type
    const format = dateFormat(field.type);

    // parse the existing value
    let date = dayjs(value, format);


    // properties to pass to the date input component
    const props = {
        label: field.label,
        slotProps: {
            textField: {
                helperText: !hasFocus && error ? error : field.helper,
                error: !hasFocus && !!error,
                required: !field.notRequired,
                variant: 'outlined' as TextFieldVariants,
                fullWidth: fullWidth,
                onFocus: () => setHasFocus(true),
                onBlur: () => {
                    setHasFocus(false);
                    setError(validateField(field, value));
                }
            },
        },
        value: date,

        onChange: (v: Dayjs | null) => {
            let value = v?.format(format) ?? "";
            onChange(value);
            setError(validateField(field, value));
        },
        disabled: disabled
    }

    // create mui date picker based on field type
    if (field.type === FieldType.Date) {
        let dateField = field as FieldDate;
        return <DatePicker {...props} minDate={dateField.minDate} maxDate={dateField.maxDate} />
    } else if (field.type === FieldType.Time) {
        let timeField = field as FieldTime;
        return <TimePicker {...props} minTime={timeField.minTime} maxTime={timeField.maxTime} />
    }
    let dateField = field as FieldDateTime;
    return <DateTimePicker {...props} minDate={dateField.minDate} maxDate={dateField.maxDate} />
}

function getOptions<T>(field: FieldSelect<T>, check: boolean, loadValues?: OptionType<T>) {
    let labels = {} as any;
    let values = {} as any;

    return (loadValues ?? field.values).map((opt) => {
        let label: string
        if (field.labelFor !== undefined) {
            label = field.labelFor(opt as T);
        } else {
            label = (opt as { label: string }).label;
        }

        let value: T
        if (field.valueFor !== undefined) {
            value = field.valueFor(opt as T);
        } else {
            value = (opt as { value: T }).value;
        }

        if (check) {
            if (labels[label]) console.error("Duplicate label", label, "in selection");
            if (values[value]) console.error("Duplicate label", label, "in selection");

            labels[label] = true;
            values[value] = true;
        }

        return { label, value }
    })
};

function SelectInput<T>({ value, onChange, field, fullWidth, setError, error, disabled }: InputProps<T>) {
    const shouldLoad = (field as FieldLoadSelect<T>).loader !== undefined;

    const [open, setOpen] = useState(false);
    const [hasFocus, setHasFocus] = useState(false);
    const [loading, setLoading] = useState(false);
    const [options, setOptions] = useState(shouldLoad ? [] : (field as FieldSelect<T>).options);


    const handleOpen = () => {
        setOpen(true);

        if (!shouldLoad) return;

        setOptions([]);
        (async () => {
            const select = field as FieldLoadSelect<T>;
            setLoading(true);
            const data = getOptions(select as any, true, await select.loader())
            setLoading(false);
            setOptions(data);
        })();
    };

    const handleClose = () => {
        setOpen(false);
    };

    const selected = (!!value ? options.find((v) => v.value == value) : null) ?? null;

    return <Autocomplete
        disablePortal
        open={open}
        onOpen={handleOpen}
        onClose={handleClose}
        options={options}
        disabled={disabled}
        renderInput={(params) => <TextField {...params} label={field.label}
            variant="outlined"
            required={!field.notRequired}
            helperText={(!hasFocus && error) ? error : field.helper}
            error={!!error && !hasFocus}
            onFocus={() => setHasFocus(true)}
            onBlur={() => {
                setHasFocus(false);
                setError(validateField(field, value));
            }}
            slotProps={{
                input: {
                    ...params.InputProps,
                    endAdornment: (
                        <>
                            {loading ? <CircularProgress color="inherit" size={20} /> : null}
                            {params.InputProps.endAdornment}
                        </>
                    ),
                },
            }}
        />}
        value={selected}
        onChange={(_, v) => onChange(v?.value)
        }
        fullWidth={fullWidth}
    />
}


export default DataForm