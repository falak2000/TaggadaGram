import { makeStyles } from '@material-ui/core/styles';

export default makeStyles((theme) => ({
    paper: {
        marginTop: theme.spacing(8),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        padding: theme.spacing(5),
        backgroundColor: 'gray',
    },
    root: {
        '& .MuiTextField-root': {
            margin: theme.spacing(1),
            display: 'flex',
        },
    },
    avatar: {
        margin: theme.spacing(1),
        backgroundColor: theme.palette.secondary.main,
    },
    form: {
        width: '50%', // Fix IE 11 issue.
        height: '100%',
        padding: 2,
        marginTop: theme.spacing(1),
        backgroundColor: 'cyan',

    },
    submit: {
        margin: theme.spacing(3, 0, 2),

    },
    googleButton: {
        marginBottom: theme.spacing(2),

    },
}));