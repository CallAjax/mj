import { createStore, applyMiddleware } from 'redux'
import { combineReducers } from 'redux-immutable'
import Immutable from 'immutable'
import createSagaMiddleware from 'redux-saga'
import rootSaga from 'sagas'
import login from './login'


const sagaMiddleware = createSagaMiddleware()
const rootReducer = combineReducers({
    login,
})

const store = createStore(
    rootReducer,
    Immutable.Map(),
    applyMiddleware(sagaMiddleware),
)
sagaMiddleware.run(rootSaga)


export default store

