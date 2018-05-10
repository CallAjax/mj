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
            const obj = {'/login': {}}
            action.props.updateRoutes(obj, 'routes')
            if (action.props.location.pathname !== '/login') {
                action.props.history.replace('/login')
            }
        } else {//需要验证本地信息
            localforage.getItem('auth').then(auth => {
                post('/auth/token').then((t) => {
                    if(t.auth !== undefined) {
                        action.props.updateRoutes(t.auth)
                    } else {
                        action.props.updateRoutes(auth)
                    }
                    if(action.props.location.pathname === '/login' || action.props.location.pathname === '/') {
                        action.props.history.replace('/home')
                    }
                })
            })

        }
    })
}




