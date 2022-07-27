import * as api from '../api/index';

export const signin = (formData, history) => async (dispatch) => {
    try {
        const res = await api.signIn(formData);


        history.push('/');
    } catch (error) {
        console.log(error);
    }
}

export const signup = (formData, history) => async (dispatch) => {
    try {
        const res = await api.signUp(formData);
         
        history.push('/');
    } catch (error) {
        console.log(error);
    }
}