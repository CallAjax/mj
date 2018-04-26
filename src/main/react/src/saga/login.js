import { select, put,call } from 'redux-saga/effects'
import { post } from 'commonjs/request'
import qs from 'qs'

export function* login() {
    const user = yield select(v => v.get('login'))
    const params = {
        uid: user.get('name'),
        pwd: user.get('password'),
    }

    /**security接收参数需要序列化，单独使用qs转换**/
    const result = yield call(post, '/manage/login', qs.stringify(params))
}

export function* changeCode() {
    const result = yield call(post, '/image')
    document.getElementById('verifyCode').src = 'data:image/jpeg;base64,'+result.data

}
