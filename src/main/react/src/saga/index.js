import { takeEvery } from 'redux-saga'
import { LOGIN_COMMIT, CHANGE_CODE } from 'actionTypes'
import { login, changeCode } from './login'


export default function* () {

    yield [
        takeEvery(LOGIN_COMMIT, login),
        takeEvery(CHANGE_CODE, changeCode),
    ]
}