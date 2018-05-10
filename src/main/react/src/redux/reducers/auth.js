import {
    UPDATE_ROUTES,
} from 'actionTypes'
import Immutable from 'immutable'

const initialState = Immutable.fromJS({
    routes: {},
    menu: {}
})

export default (state = initialState, action) => {
    switch (action.type) {
        case UPDATE_ROUTES:
            if(action.name !== undefined) {
                return state.set(action.name,Immutable.fromJS(action.obj))
            } else {
                return state.merge(Immutable.fromJS(action.obj))
            }
        default:
            return state
    }
}