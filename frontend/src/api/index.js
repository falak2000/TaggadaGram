import axios from 'axios';

const API = axios.create('');

export const signUp = (formData) => API.post('/user/signup',formData);
export const signIn = (formData) => API.post('/user/signin',formData);