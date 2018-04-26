import {
    CHANGE_LOGIN_FORM,
    LOGIN_COMMIT,
    CHANGE_CODE,
} from 'actionTypes'


export const changeLoginForm = (obj) => {
    return { type: CHANGE_LOGIN_FORM, obj}
}

export const loginCommit = () => {
    return { type: LOGIN_COMMIT }
}

export const changeCode = () => {
    return { type:CHANGE_CODE }
}