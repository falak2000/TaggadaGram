import React, { useState } from 'react';
import { Avatar, Button, Paper, Grid, Typography, Container } from "@material-ui/core";
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import Input from './Input';
import useStyles from './styles';

const Auth = () => {

    const classes = useStyles();

    const [showPassword, setShowPassword] = useState(false);

    const isSignup = true;

    const handleShowPassword = () => setShowPassword((prevShowPassword) => !prevShowPassword);


    const handleSubmit = () => {

    };

    const handleChange = () => {

    }

    return (
        <Container component="main" maxwidth="xs">
            <Paper className={classes.paper} elevation={3}>
                <Avatar className={classes.Avatar}>
                    <LockOutlinedIcon />
                </Avatar>

                <Typography variant="h5"> {isSignup ? 'Sign Up' : ' Sign In'}</Typography>
                <form className={classes.form} onSubmit={handleSubmit}>
                    <Grid container spacing={2} direction='column'>
                        {
                            isSignup && (
                                <>
                                    <Grid container spacing={2} direction='row'>
                                        <Input name="firstName" label="First Name" handleChange={handleChange} autoFocus half></Input>
                                        <Input name="lastName" label="Last Name" handleChange={handleChange} half ></Input>
                                    </Grid>

                                </>

                            )
                        }

                        <Input fullWidth="true" name="email" label="Email Address" handleChange={handleChange} type="email" />

                        <Grid container spacing={2} direction='row'>
                            <Input fullWidth name="password" label="Password" handleChange={handleChange} type={showPassword ? "text" : "password"} handleShowPassword={handleShowPassword} />

                            {isSignup && <Input name="confirmPassword" label="Reapeat Password" handleChange={handleChange} type="password" />}
                        </Grid>

                    </Grid>

                    <Button type="submit" fillwidth varient="contained" color="primary"></Button>
                    {isSignup ? 'Sign Up' : 'Sign In'}
                </form>
            </Paper>
        </Container>
    )

}

export default Auth;