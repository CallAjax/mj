import {
    CHANGE_LOGIN_FORM,
    LOGIN_COMMIT,
    CHANGE_CODE,
} from 'actionTypes'


export const changeLoginForm = (obj) => {
    return { type: CHANGE_LOGIN_FORM, obj}
}

export const loginCommit = (props) => {
    return { type: LOGIN_COMMIT, props }
}

export const changeCode = () => {
    return { type:CHANGE_CODE }
}