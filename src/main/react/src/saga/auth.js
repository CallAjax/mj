import store from 'store'
import { post } from 'commonjs/request'
import {
    UPDATE_ROUTES,
} from 'actionTypes'

export function* getRoutes(action) {
    const url = action.props.location.pathname
    const auth = store.get('auth')
    if(auth === undefined) {
        const obj = {'/login': {}}
        action.props.updateRoutes(obj, 'routes')
        if (url !== '/login' && url !== '/error') {
            action.props.history.replace('/login')
        }
    } else {//需要验证本地信息
        post('/auth/token').then((res) => {
            if(res.auth !== undefined) {
                action.props.updateRoutes(res.auth)
            } else {
                action.props.updateRoutes(auth)
            }
            if(url === '/' || url === '/login') {
                action.props.history.push('/home')
            }
        })
    }
}




