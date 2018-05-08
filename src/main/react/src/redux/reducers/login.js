import {
    CHANGE_LOGIN_FORM,
} from 'actionTypes'
import Immutable from 'immutable'

const initialState = Immutable.fromJS({
    name: '',
    password: '',
    codeShow: 'none',
    code: '',
    src: '',
    disabled: false,
    msgShow: false,
    msg: '',
    loading: 'determinate',
    codeId: '',
    status: 0,
})


export default (state = initialState, action) => {
    switch (action.type) {
        case CHANGE_LOGIN_FORM:
            return state.merge(Immutable.fromJS(action.obj))
        default:
            return state
    }
}