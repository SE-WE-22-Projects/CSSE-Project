import { Autocomplete, Box, Button, CircularProgress, Stack, StackOwnProps, TextField, TextFieldVariants } from '@mui/material'
import { DatePicker, DateTimePicker, TimePicker } from '@mui/x-date-pickers'
import dayjs, { Dayjs } from 'dayjs'
import { MutableRefObject, useEffect, useRef, useState } from 'react'

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

export type OptionType<T> = { label: string, value: T }


interface FieldSelectBasic<T, D = T> extends FieldBasic<T> {
    type: FieldType.Select,

    /**
    * A function that returns the label for the given option
    * Ex: the name from a person object
    * @param o the option
    * @returns the label to display
    */
    labelFor?: (o: D) => string;

    /**
     * A function that should return the value for the given option
     * Ex: the personId from a person object
     * @param o the option
     * @returns the value for selecting this option
     */
    valueFor?: (o: D) => any;


    getInitial?: (o: any) => T;

    /**
     * Internal property. DO NOT USE.
     */
    options: { label: string, value: T }[]
}

interface FieldSelect<T> extends FieldSelectBasic<T> {
    values: OptionType<T>[]
}


interface FieldLoadSelect<T> extends FieldSelectBasic<T> {
    type: FieldType.Select,
    loader: () => Promise<OptionType<T>[]>
}

interface FieldLoadSelect2<T, D> extends FieldSelectBasic<T, D> {
    type: FieldType.Select,

    /**
     * A function that returns the label for the given option
     * Ex: the name from a person object
     * @param o the option
     * @returns the label to display
     */
    labelFor: (o: D) => string;

    /**
     * A function that should return the value for the given option
     * Ex: the personId from a person object
     * @param o the option
     * @returns the value for selecting this option
     */
    valueFor: (o: D) => T;

    loader: () => Promise<D[]>
}

type AllSelectFields<T> = FieldLoadSelect<T> | FieldLoadSelect2<T, any> | FieldSelect<T>

type AllFields = FieldText | FieldNumber | FieldEmail | FieldPhone | FieldDate | FieldTime | FieldDateTime | FieldSelect<any> | FieldLoadSelect<any> | FieldLoadSelect2<any, any>


type FieldProps<T extends AllFields> = Omit<Omit<Omit<T, 'type'>, 'internal'>, 'label'>;

interface FieldTypes {
    text(label: string, field?: FieldProps<FieldText>): FieldText
    email(label: string, field?: FieldProps<FieldEmail>): FieldEmail
    phone(label: string, field?: FieldProps<FieldPhone>): FieldPhone
    date(label: string, field?: FieldProps<FieldDate>): FieldDate
    time(label: string, field?: FieldProps<FieldTime>): FieldTime
    dateTime(label: string, field?: FieldProps<FieldDateTime>): FieldDateTime
    number(label: string, field?: FieldProps<FieldNumber>): FieldNumber

    select<T>(label: string, field?: Omit<FieldProps<FieldSelect<T>>, 'options'>): FieldSelect<T>
    select<T>(label: string, field?: Omit<FieldProps<FieldLoadSelect<T>>, 'options'>): FieldLoadSelect<T>
    select<T, D>(label: string, field?: Omit<FieldProps<FieldLoadSelect2<T, D>>, 'options'>): FieldLoadSelect2<T, D>
}

export const Field: FieldTypes = {

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

    select: (label: string, field: any) => {
        if ((field as any).loader !== undefined) {
            return { ...(field as any), label, type: FieldType.Select };
        } else {
            return { ...(field as any), label, type: FieldType.Select, options: getOptions(field as any, true) }
        }
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


export type FormState<T extends Fields,> = {
    [K in keyof T]: {
        value: NonNullable<T[K]['__internal']>
        rawValue?: any

        valid: boolean
    }
}



const getDateFormat = (typ: FieldType) => typ === FieldType.Date ? "YYYY-MM-DD" : typ === FieldType.Time ? "HH:mm:ss" : "YYYY-MM-DD HH:mm:ss";
const dateSeconds = (date: Dayjs) => (date.hour() * 60 + date.minute()) * 60 + date.second();
const debugValidate = false;

/**
 * Validates the given field. This returns a string explaining the validation error if one is found.
 * @param field the field being validated
 * @param value the current value of the field
 * @returns a string explaining the validation error if one is found. null or undefined otherwise.
 */
const validateField = (field: FieldBasic<any>, value: any, extra?: any): string | null | undefined => {
    if (debugValidate)
        console.debug(`validate: ${typeof value} '${value}'  as ${FieldType[field.type]}`);


    // check if the required field is given.
    if ((value === null || value === undefined || value === "")) {
        if (!field.notRequired)
            return field.label + " is required";

        // value is not set and field is not required.
        // skip validation.
        return;
    }

    switch (field.type) {
        case FieldType.Text:
            // text length validations.
            let length = (value as string).length;
            let minLength = (field as FieldText).minLength || 0;
            let maxLength = (field as FieldText).maxLength || Infinity;

            if (length < minLength) {
                return `${field.label} must be at least ${minLength} letters.`
            } else if (length > maxLength) {
                return `${field.label} must be less than ${maxLength} letters.`
            }
            break;

        case FieldType.Email:
            // email validation
            if (!emailRegex.test(value)) {
                return `Please enter a valid email address`;
            }
            break;

        case FieldType.Phone:
            // phone number validation
            if (!phoneRegex.test(value)) {
                return `Please enter a valid phone number`;
            }
            break;

        case FieldType.Number:
            // number validation
            let numField = field as FieldNumber;
            let parsed = typeof value === 'number' ? value : parseFloat(value);
            let strValue = typeof value === 'string' ? value : parsed.toString();

            if (Number.isNaN(parsed) || !Number.isFinite(parsed) || !numberRegex.test(strValue)) {
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
            break;

        case FieldType.DateTime:
        case FieldType.Date:
            let dateField = field as FieldDateTime | FieldDate;
            let dateFormat = getDateFormat(field.type);
            let date = dayjs(value, dateFormat, true);

            if (!date.isValid()) return "Please select a valid date";
            else if (dateField.maxDate !== undefined && date.isAfter(dateField.maxDate)) {
                return `Please select a date before ${dateField.maxDate.format(dateFormat)}`
            } else if (dateField.minDate !== undefined && date.isBefore(dateField.minDate)) {
                return `Please select a date after ${dateField.minDate.format(dateFormat)}`
            }
            break

        case FieldType.Time:
            let timeField = field as FieldTime;
            let format = getDateFormat(field.type);
            let time = dayjs(value, format, true);

            if (!time.isValid()) return "Please select a valid time";
            else if (timeField.minTime && dateSeconds(time) < dateSeconds(timeField.minTime)) {
                return `Please select a time after ${timeField.minTime.format(format)}`
            } else if (timeField.maxTime && dateSeconds(time) > dateSeconds(timeField.maxTime)) {
                return `Please select a time before ${timeField.maxTime.format(format)}`
            }
            break;
        case FieldType.Select:
            if (extra?.find !== undefined) {
                let selected = extra.find((v: any) => v.value === value);
                if (selected === undefined) {
                    return `Please select a valid value`;
                }
            }

            break;
        default:
            throw "Unsupported form field type"
    }

    // call custom validators if given.
    if (field.validator !== undefined) {
        return field.validator(value);
    }
}


function DataForm<T extends Fields,>(props: FormProps<T>) {
    const [isSubmitting, setIsSubmitting] = useState(false);
    const [revalidate, setRevalidate] = useState(0);

    const formState = useRef<Partial<FormState<T>>>({});

    const submit = async () => {
        let canSubmit = true;

        let data = {} as any;

        // validate all fields
        Object.entries(props.fields).forEach((f) => {
            let field = formState.current[f[0]];
            let value = formState.current[f[0]]?.value;
            if (field === undefined || !field.valid) {
                let error = validateField(f[1], value);
                console.debug(`Validation error in ${f[0]}: ${error}`)
                canSubmit = false;
                data[f[0]] = { invalid: value }
            } else {
                data[f[0]] = formState.current[f[0]]?.value;
            }
        });
        console.debug("Form state is", data);


        if (!canSubmit) {
            setRevalidate(revalidate + 1);
            return;
        }


        if (props.onSubmit !== undefined) {
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

                const fieldProps: InputFieldProps<any> = {
                    field: field,
                    formKey: key,
                    state: formState,

                    initial: props.initial?.[key],
                    hasInitial: props.initial !== undefined,
                    revalidate: revalidate,

                    initialState: props.initial,

                    inputProps: {
                        label: field.label,
                        disabled: isSubmitting,
                        fullWidth: props.fullWidth,
                        required: !field.notRequired,
                        variant: 'outlined'
                    },
                }

                if (field.type === FieldType.Date || field.type === FieldType.Time || field.type == FieldType.DateTime) {
                    return <DateInput key={idx} {...fieldProps as InputFieldProps<string>} />
                } else if (field.type === FieldType.Number) {
                    return <NumberInput key={idx} {...fieldProps as InputFieldProps<number>} />
                } else if (field.type === FieldType.Select) {
                    return <SelectInput key={idx} {...fieldProps as InputFieldProps<any>} />
                } else if (field.type === FieldType.Text || field.type === FieldType.Email || field.type === FieldType.Phone) {
                    return <StringInput key={idx} {...fieldProps as InputFieldProps<string>} />
                } else {
                    throw `Unsupported form field type ${FieldType[(field as any).type]}`
                }

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

type InputFieldProps<T, F extends AllFields = any, V extends Fields = Fields, K extends keyof V = any> = {
    formKey: K
    field: F,
    initial?: T
    hasInitial: boolean
    state: MutableRefObject<Partial<FormState<V>>>

    initialState: any

    revalidate: number

    inputProps: MUIInputProps
}

type MUIInputProps = {
    label: string,
    disabled?: boolean,
    required?: boolean,
    variant?: TextFieldVariants
    fullWidth?: boolean,
}

/**
 * An input that takes a string value.
 */
const StringInput = (props: InputFieldProps<string, FieldText> & { parser?: (v?: string) => any }) => {
    const [value, setValue] = useState(props.hasInitial ? props.initial : undefined);
    const [error, setError] = useState<string | null | undefined>();
    const [hasFocus, setHasFocus] = useState(false);

    // validates the current value of the field.
    const validate = () => {
        let validationMsg = validateField(props.field, value)
        props.state.current[props.formKey]!.valid = validationMsg === undefined || validationMsg === null;
        setError(validationMsg);
    }

    // copy the initial value to the state and validate it.
    useEffect(() => {
        props.state.current[props.formKey] = {
            value: props.hasInitial ? props.initial : undefined,
            valid: false
        };

        if (props.hasInitial) validate();
    }, [props.initial]);

    // revalidate the input value
    useEffect(() => {
        if (props.revalidate !== 0) validate();
    }, [props.revalidate]);



    return <TextField
        {...props.inputProps}
        helperText={(!hasFocus && error) ? error : props.field.helper}
        error={!!error && !hasFocus}
        value={value ?? ""}
        onChange={(v) => {
            let data = v.target.value;
            setValue(data);

            if (props.parser) data = props.parser(data);
            props.state.current[props.formKey] = {
                value: data,
                valid: false
            };

        }}
        onFocus={() => setHasFocus(true)}

        onBlur={() => {
            validate();
            setHasFocus(false);
        }}

        multiline={props.field.textArea}

    />
}


/**
 * An input field that takes a number as the input
 */
const NumberInput = (props: InputFieldProps<number>) => {
    return <StringInput  {...props as InputFieldProps<string>} parser={(v) => {
        // try to parse the number
        let num: number
        if (v === undefined || !numberRegex.test(v)) {
            num = NaN;
        } else {
            num = Number.parseFloat(v);
        }
        return num;
    }} />
}

/**
 * An input field that handles date/time based selections.
 */
const DateInput = (props: InputFieldProps<string>) => {
    // select date format based on field type
    const format = getDateFormat(props.field.type);

    const [value, setValue] = useState<Dayjs | null>(props.hasInitial ? dayjs(props.initial, format, true) : null);
    const [error, setError] = useState<string | null | undefined>();
    const [hasFocus, setHasFocus] = useState(false);



    // validates the current value of the field.
    const validate = () => {
        let validationMsg = validateField(props.field, props.state.current[props.formKey]!.value)
        props.state.current[props.formKey]!.valid = validationMsg === undefined || validationMsg === null;
        setError(validationMsg);
    }

    // copy the initial value to the state and validate it.
    useEffect(() => {
        props.state.current[props.formKey] = {
            value: props.hasInitial ? props.initial : undefined,
            valid: false
        };

        if (props.hasInitial) validate();
    }, [props.initial]);

    // revalidate the input value
    useEffect(() => {
        if (props.revalidate !== 0) validate();
    }, [props.revalidate]);


    // properties to pass to the date input component
    const inputProps = {
        ...props.inputProps,
        slotProps: {
            textField: {
                ...props.inputProps,
                helperText: !hasFocus && error ? error : props.field.helper,
                error: !hasFocus && !!error,
                onFocus: () => setHasFocus(true),
                onBlur: () => {
                    setHasFocus(false);
                    setError(validateField(props.field, value));
                }
            },
        },
        value: value,

        onChange: (v: Dayjs | null) => {
            setValue(v);

            props.state.current[props.formKey] = {
                value: v != null ? v.format(format) : undefined,
                valid: false
            };

            validate();
        },
    }

    // create mui date picker based on field type
    if (props.field.type === FieldType.Date) {
        let dateField = props.field as FieldDate;
        return <DatePicker {...inputProps} minDate={dateField.minDate} maxDate={dateField.maxDate} />
    } else if (props.field.type === FieldType.Time) {
        let timeField = props.field as FieldTime;
        return <TimePicker {...inputProps} minTime={timeField.minTime} maxTime={timeField.maxTime} />
    }
    let dateField = props.field as FieldDateTime;
    return <DateTimePicker {...inputProps} minDate={dateField.minDate} maxDate={dateField.maxDate} />
}

function getOptions<T, F extends AllSelectFields<T>>(field: F, check: boolean, loadValues?: OptionType<T>[]) {
    let labels = {} as any;
    let values = {} as any;

    return (loadValues ?? (field as any).values).map((opt: OptionType<T>) => {
        let option = parseOption<T, F>(field as any, opt);


        if (check) {
            if (labels[option.label]) console.error("Duplicate label", option.label, "in selection");
            if (values[option.value]) console.error("Duplicate value", option.value, "in selection");

            labels[option.label] = true;
            values[option.value] = true;
        }

        return option;
    })
};

function parseOption<T, F extends AllSelectFields<T>>(field: F, opt: OptionType<T>) {
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

    return { label, value }
}

function SelectInput<T, F extends AllSelectFields<T>>(props: InputFieldProps<T, F>) {
    const shouldLoad = (props.field as FieldLoadSelect<T>).loader !== undefined;

    const [hasFocus, setHasFocus] = useState(false);
    const [value, setValue] = useState<OptionType<T> | null>(null);
    const [error, setError] = useState<string | null | undefined>();

    const [options, setOptions] = useState(shouldLoad ? [] : (props.field as FieldSelect<T>).options);
    const [loading, setLoading] = useState(shouldLoad);

    const setInitialValue = (opts: OptionType<T>[]) => {
        const initial = props.field.getInitial !== undefined ? props.field.getInitial(props.initialState) : props.initial;
        const selected = opts.find((v) => v.value === initial);

        if (selected !== undefined) {
            props.state.current[props.formKey]!.value = initial;

            let validationMsg = validateField(props.field, initial, opts)
            props.state.current[props.formKey]!.valid = validationMsg === undefined || validationMsg === null;
            setError(validationMsg);
        }

        setValue(selected ?? null);
    }


    // validates the current value of the field.
    const validate = () => {
        let validationMsg = validateField(props.field, props.state.current[props.formKey]!.value, options)
        props.state.current[props.formKey]!.valid = validationMsg === undefined || validationMsg === null;
        setError(validationMsg);
    }

    // copy the initial value to the state and validate it.
    useEffect(() => {
        props.state.current[props.formKey] = {
            value: props.hasInitial ? props.initial : undefined,
            valid: false
        };

        if (props.hasInitial && !shouldLoad) {
            setInitialValue(options);
        }
    }, [props.initial]);

    // revalidate the input value on form revalidate or option load complete
    useEffect(() => {
        if (shouldLoad && !loading && props.hasInitial) {
            validate();
        }
    }, [props.revalidate, loading]);

    useEffect(() => {
        if (!shouldLoad) return;

        (async () => {
            const select = props.field as FieldLoadSelect<T>;
            const data = getOptions(select as any, true, await select.loader());

            if (props.hasInitial) {
                setInitialValue(data);
            }

            setLoading(false);
            setOptions(data);
        })();

    }, [props.field])

    return <Autocomplete
        disablePortal
        options={options}
        {...props.inputProps}
        disabled={props.inputProps.disabled || loading}
        renderInput={(params) => <TextField
            {...props.inputProps}
            {...params}

            helperText={(!hasFocus && error) ? error : props.field.helper}
            error={!!error && !hasFocus}
            onFocus={() => setHasFocus(true)}
            onBlur={() => {
                setHasFocus(false);
                setError(validateField(props.field, value));
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
        onChange={
            (_, v) => {
                props.state.current[props.formKey] = {
                    value: v?.value,
                    valid: false
                };
                setValue(v);

                validate();
            }
        }
        getOptionLabel={(v) => v.label}
        value={value}
    />
}


export default DataForm