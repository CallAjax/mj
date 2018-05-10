import localforage from 'localforage'
import Immutable, { is } from 'immutable'
import { select, put,call } from 'redux-saga/effects'
import { post } from 'commonjs/request'
import {
    UPDATE_ROUTES,
} from 'actionTypes'

export function* getRoutes(action) {
    localforage.getItem('Authorization').then(v => {
        if (v === null) {//没有本地信息
            const routes = Immutable.fromJS({'/login': {}})
            action.props.updateRoutes(routes)
            if (action.props.location.pathname !== '/login') {
                action.props.history.replace('/login')
            }
        } else {//需要验证本地信息
            localforage.getItem('auth').then(auth => {
                post('/auth/token').then((t) => {
                    let routes = {}
                    if(t.auth !== undefined) {
                        routes = Immutable.fromJS(t.auth.routes)
                    } else {
                        routes = Immutable.fromJS(auth.routes)
                    }
                    console.log(auth)
                    action.props.updateRoutes(routes)
                    action.props.history.replace('/home')
                })
            })

        }
    })
    return 'aaa'
}




