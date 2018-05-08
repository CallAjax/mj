import localforage from 'localforage'
import Immutable from 'immutable'
import { select, put,call } from 'redux-saga/effects'
import {
    UPDATE_ROUTES,
} from 'actionTypes'

export function* validateRoutes() {
    const auth = yield call(getAuth)
    if(auth === null) {//没有本地数据，直接显示登录页面
        const routes = Immutable.fromJS({'/login':{}})
        const update = yield put({type: UPDATE_ROUTES, routes})
    } else {

    }
}

const getAuth = async function(){
    let auth
    await localforage.getItem('Authorization').then(v => auth = v)
    return auth
}


