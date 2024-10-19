import FormControl from '@mui/material/FormControl';
import InputAdornment from '@mui/material/InputAdornment';
import OutlinedInput from '@mui/material/OutlinedInput';
import SearchRoundedIcon from '@mui/icons-material/SearchRounded';
import { ChangeEvent, useState } from 'react';

export default function Search({ onChange }: { onChange: (pattern: RegExp | null) => any }) {
    const [value, setValue] = useState("")

    // add a 0.2 second delay for calling onChange that resets every time a character changes.
    // onChange will be called when the search text remains unchanged for 0.2 seconds.
    let timeout = -1;
    const changeHandler = (c: ChangeEvent<HTMLInputElement>) => {
        const value = c.target.value.trim();
        clearTimeout(timeout);
        timeout = setTimeout(() => {
            if (value.length === 0) {
                onChange(null);
                return;
            }

            let escaped = value.replace(/[.*+?^${}()|[\]\\]/g, "\\$&");
            onChange(RegExp(escaped, 'i'));
        }, 200)

        setValue(value)
    }

    return (
        <FormControl sx={{ width: { xs: '100%', md: '25ch' } }} variant="outlined">
            <OutlinedInput
                size="small"
                value={value}
                id="search"
                placeholder="Search…"
                sx={{ flexGrow: 1 }}
                onChange={changeHandler}
                startAdornment={
                    <InputAdornment position="start" sx={{ color: 'text.primary' }}>
                        <SearchRoundedIcon fontSize="small" />
                    </InputAdornment>
                }
                inputProps={{
                    'aria-label': 'search',
                }}
            />
        </FormControl>
    );
}
