import { takeEvery } from 'redux-saga'
import {
    LOGIN_COMMIT,
    CHANGE_CODE,
    SAGA_GET_ROUTES,
} from 'actionTypes'
import { login, changeCode } from './login'
import { getRoutes } from './auth'


export default function* () {

    yield [
        takeEvery(LOGIN_COMMIT, login),
        takeEvery(CHANGE_CODE, changeCode),
        takeEvery(SAGA_GET_ROUTES, getRoutes),
    ]
}