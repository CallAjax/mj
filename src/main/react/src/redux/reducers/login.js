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
    loading: 'determinate',
    codeFlag: 0,
    codeId: '',
})


export default (state = initialState, action) => {
    switch (action.type) {
        case CHANGE_LOGIN_FORM:
            return state.merge(Immutable.fromJS(action.obj))
        default:
            return state
    }
}