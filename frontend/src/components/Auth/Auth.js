import React, { useState } from 'react';
import { Avatar, Button, Paper, Grid, Typography, Container, Link } from "@material-ui/core";
import Input from './Input';
import useStyles from './style';

const initial = {firstName:'', lastName:'', email:'', password:'', confirmPassword:'' };

const Auth = () => {

    const classes = useStyles();

    const [showPassword, setShowPassword] = useState(false);
    const [isSignup, setIsSignup] = useState(false);
    const [formData, setFormData] = useState(initial);

    const handleShowPassword = () => setShowPassword((prevShowPassword) => !prevShowPassword);


    const handleSubmit = () => {
        try {
            if(isSignup){
                
            }
        } catch (error) {
            
        }
    };

    const handleChange = (e) => {
        setFormData({...formData, [e.target.name]:e.target.value});
        console.log(formData);
    }

    return (
        <Container component="main" maxwidth="xs">
            <Paper className={classes.paper} elevation={3}>

                <Typography variant="h5"> {isSignup ? 'Sign Up' : ' Sign In'}</Typography>
                <Paper component='form' className={classes.form} onSubmit={handleSubmit} elevation={1}>
                    <Grid container spacing={2}>
                        {
                            isSignup && (
                                <>
                                        <Input name="firstName" label="First Name" handleChange={handleChange} autoFocus half></Input>
                                        <Input name="lastName" label="Last Name" handleChange={handleChange} half ></Input>
                                </>

                            )
                        }

                        <Input name="email" label="Email Address" handleChange={handleChange} type="email"  />

                        <Input name="password" label="Password" handleChange={handleChange} type={showPassword ? "text" : "password"} handleShowPassword={handleShowPassword} />

                        {isSignup && <Input name="confirmPassword" label="Confirm Password" handleChange={handleChange} type="password" />}
                                        
                    </Grid>
                    <Button type="submit" fullWidth variant="contained" color="primary" className={classes.submit}>
                        {isSignup ? 'Sign Up' : 'Sign In'}
                    </Button>
                    {isSignup ?
                        <Typography variant='body2'>
                            Already have an account? 
                            <Link className={classes.link} onClick={()=>setIsSignup(false)}>Login</Link>
                        </Typography>
                        :
                        <Typography variant='body2'>
                            Do not have an account? 
                            <Link className={classes.link} onClick={()=>setIsSignup(true)}>Create Account</Link>
                        </Typography>
                    }
                    
                </Paper>
            </Paper>
        </Container>
    )

}

export default Auth;