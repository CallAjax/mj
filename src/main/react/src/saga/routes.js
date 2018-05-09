import localforage from 'localforage'
import Immutable from 'immutable'
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
            post('/auth/token').then(() => {
                console.log(1111111)
            })
        }
    })
}




