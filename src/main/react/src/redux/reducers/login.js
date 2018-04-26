import {
    CHANGE_LOGIN_FORM,
} from 'actionTypes'
import Immutable from 'immutable'

const initialState = Immutable.fromJS({
    name: '',
    password: '',
    isShow: 'none',
    code: '',
    src: '',
    disabled: false,
    msgShow: false,
    msg: '',
})


export default (state = initialState, action) => {
    switch (action.type) {
        case CHANGE_LOGIN_FORM:
            return state.set(action.id, action.value)
        default:
            return state
    }
}