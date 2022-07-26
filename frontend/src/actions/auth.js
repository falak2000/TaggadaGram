import * as api from '../api/index';

export const signin = (formData, history) => async (dispatch) => {
    try {
        //login
        history.push('/');
    } catch (error) {
        console.log(error);
    }
}

export const signup = (formData, history) => async (dispatch) => {
    try {
        //register  
        history.push('/');
    } catch (error) {
        console.log(error);
    }
}