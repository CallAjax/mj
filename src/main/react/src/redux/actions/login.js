import {
    CHANGE_LOGIN_FORM,
    LOGIN_COMMIT,
    CHANGE_CODE,
} from 'actionTypes'


export const changeLoginForm = (id, value) => {
    return { type: CHANGE_LOGIN_FORM, id, value}
}

export const loginCommit = () => {
    return { type: LOGIN_COMMIT }
}

export const changeCode = () => {
    return { type:CHANGE_CODE }
}