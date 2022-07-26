import React, {useState} from 'react';
import { AppBar, Typography, Toolbar, Avatar, Menu, MenuItem, Tooltip, IconButton } from '@material-ui/core';

import useStyles from './style';
import Search from './Search';

const Navbar = () => {

    const [anchorEl, setAnchorEl] = useState(null);
    const open = Boolean(anchorEl);

    const classes = useStyles();
    const user = 'praf';

    const handleClose = () => {
        setAnchorEl(null);
    }
    const handleClick = (e) => {
        setAnchorEl(e.currentTarget);
    }

  return (
        <AppBar position='static' className={classes.appBar} elevation={0} >
            <div className={classes.brandContainer}>
                //logo
                <Typography variant='h4' align='center' >TaggadaGram</Typography>
            </div>
            <Toolbar className={classes.toolbar}>
                { user && (<>
                    <Search />
                    <Tooltip title='Profile'> 
                        <IconButton 
                            onClick={handleClick}
                            size="small"
                            sx={{ ml: 2 }}
                            aria-controls={open ? 'account-menu' : undefined}
                            aria-haspopup="true"
                            aria-expanded={open ? 'true' : undefined}
                        >
                            <Avatar alt={user} ></Avatar>

                        </IconButton>
                    </Tooltip>

                    <Menu 
                            id='profile-menu'
                            anchorEl={anchorEl}
                            open={open}
                            onClose={handleClose}
                            onClick={handleClose}
                            anchorOrigin = {{horizontal: 'left', vertical: 'bottom'}}
                            getContentAnchorEl={null}
                        >
                            <MenuItem>Profile</MenuItem>
                            <MenuItem>Logout</MenuItem>
                        </Menu>
                    </>
                )}
            </Toolbar>

        </AppBar>
  );
}

export default Navbar;



