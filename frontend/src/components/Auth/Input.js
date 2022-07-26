import React from "react";
import { TextField, Grid, InputAdornment, IconButton } from "@material-ui/core";
//import { InputAdornment } from '@mui/material';
import Visibility from '@material-ui/icons/Visibility';
import VisibilityOff from '@material-ui/icons/Visibility';



const Input = ({ name, handleChange, label, half, autoFocus, type, handleShowPassword }) => {
    return (
        <Grid item xs={12} sm={6}>
            <TextField
                name={name}
                onChange={handleChange}
                variant="outlined"
                required
                label={label}
                autoFocus={autoFocus}
                type={type}
                InputProps={name === 'password' && {
                    endAdornment: (
                        <InputAdornment position="end">
                            <IconButton onClick={handleShowPassword}>
                                {type === 'password' ? <Visibility /> : <VisibilityOff />}
                            </IconButton>
                        </InputAdornment>
                    ),
                }}
            />
        </Grid>
    )
}

export default Input;