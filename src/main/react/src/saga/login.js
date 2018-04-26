import { select, put,call } from 'redux-saga/effects'
import { post } from 'commonjs/request'
import qs from 'qs'
import {
    CHANGE_LOGIN_FORM,
} from 'actionTypes'

export function* login() {
    const login = yield select(v => v.get('login'))
    const params = {
        uid: login.get('name'),
        pwd: login.get('password'),
    }
    let obj = {}

    /**security接收参数需要序列化，单独使用qs转换**/
    try {
        //提交按钮不可用
        obj = {disabled: true, msgShow: false,loading: 'indeterminate'}
        yield put({type:CHANGE_LOGIN_FORM,obj})
        const result = yield call(post, '/manage/login', qs.stringify(params))
        if(result.data.resCode === 'SUCCESS') {

        } else {
            obj = getError(result.data.resMsg)
            yield put({type:CHANGE_LOGIN_FORM,obj})
        }
    } catch (e) {
        
    }


}


const getError = msg => {
    return {
        name: '',
        password: '',
        code: '',
        disabled: false,
        msgShow: true,
        msg: msg,
        loading: 'determinate',
    }
}

export function* changeCode() {
    const result = yield call(post, '/image')
    document.getElementById('verifyCode').src = 'data:image/jpeg;base64,'+result.data

}

