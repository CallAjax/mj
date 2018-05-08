import { takeEvery } from 'redux-saga'
import {
    LOGIN_COMMIT,
    CHANGE_CODE,
    SAGA_VALIDATE_ROUTES,
} from 'actionTypes'
import { login, changeCode } from './login'
import { validateRoutes } from './routes'


export default function* () {

    yield [
        takeEvery(LOGIN_COMMIT, login),
        takeEvery(CHANGE_CODE, changeCode),
        takeEvery(SAGA_VALIDATE_ROUTES, validateRoutes),
    ]
}