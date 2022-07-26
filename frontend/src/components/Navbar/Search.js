import React, {useState} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import { Paper, InputBase, IconButton } from '@material-ui/core';
import SearchIcon from '@material-ui/icons/SearchSharp';

const useStyles = makeStyles((theme) => ({
  root: {
    margin: theme.spacing(1,2),
    display: 'flex',
    alignItems: 'center',
    width: 400,
  },
  input: {
    marginLeft: theme.spacing(1),
    flex: 1,
    width:theme.spacing(15)
  },
  iconButton: {
    padding: theme.spacing(0.8),
  }
}));

export default function CustomizedInputBase() {
  const classes = useStyles();

  const [searchPhrase, setSearchPhrase] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log(searchPhrase);
  }

  return (
    <Paper component="form" className={classes.root} onSubmit={handleSubmit} >
      <InputBase
        className={classes.input}
        placeholder="Search..."
        value={searchPhrase}
        onChange={(e)=>{setSearchPhrase(e.target.value);console.log(searchPhrase);}}
        inputProps={{ 'aria-label': 'search' }}
      />
      <IconButton type="submit" className={classes.iconButton} aria-label="search">
        <SearchIcon />
      </IconButton>
    </Paper>
  );
}
