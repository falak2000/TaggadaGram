import { LOGIN, REGISTER } from "../constants/actionTypes";

const authReducer = (user = {}, action) =>{
    switch (action.type) {
        case LOGIN:
            return action.payload;
        case REGISTER :
            return action.payload;
        default:
            return user;
    }
}

export default authReducer;