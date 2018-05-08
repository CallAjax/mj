import {
    UPDATE_ROUTES,
} from 'actionTypes'
import Immutable from 'immutable'

const initialState = Immutable.fromJS({
    '/error': {},
})

export default (state = initialState, action) => {
    switch (action.type) {
        case UPDATE_ROUTES:
            return state.merge(action.routes)
        default:
            return state
    }
}