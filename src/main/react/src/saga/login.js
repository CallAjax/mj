import { select, put,call } from 'redux-saga/effects'
import { post } from 'commonjs/request'
import qs from 'qs'
import {
    CHANGE_LOGIN_FORM,
    CHANGE_CODE,
} from 'actionTypes'

export function* login() {
    let obj = {}
    const login = yield select(v => v.get('login'))
    const params = {
        uid: login.get('name'),
        pwd: login.get('password'),
        code: login.get('code'),
        codeId: login.get('codeId'),
    }

    if(params.uid === null || params.uid === '') {
        obj = getError('请输入用户名...')
        yield put({type:CHANGE_LOGIN_FORM,obj})
        return false
    }
    if(params.pwd === null || params.pwd === '') {
        obj = getError('请输入密码...')
        yield put({type:CHANGE_LOGIN_FORM,obj})
        return false
    }
    if(login.get('codeShow') === ''){
        if(params.code === null || params.code === ''){
            obj = getError('请输入验证码...')
            yield put({type:CHANGE_LOGIN_FORM,obj})
            return false
        }
    }

    /**security接收参数需要序列化，单独使用qs转换**/
    try {
        //提交按钮不可用
        obj = {disabled: true, msgShow: false,loading: 'indeterminate'}
        yield put({type:CHANGE_LOGIN_FORM,obj})
        const result = yield call(post, '/manage/login', qs.stringify(params))
        if(result.data.resCode === 'SUCCESS') {

        } else {
            yield put({type:CHANGE_CODE})
            obj = getError(result.data.resMsg,{codeShow:''})
            yield put({type:CHANGE_LOGIN_FORM,obj})
        }
    } catch (e) {
        obj = getError('系统繁忙，请稍后再试...')
        yield put({type:CHANGE_LOGIN_FORM,obj})
        return false
    }


}


const getError = (msg,args) => {
    let result =  {
        name: '',
        password: '',
        code: '',
        disabled: false,
        msgShow: true,
        msg: msg,
        loading: 'determinate',
    }
    return Object.assign(result, args)
}

export function* changeCode() {
    const result = yield call(post, '/image')
    const obj = {src:result.data}
    yield put({type:CHANGE_LOGIN_FORM,obj})
}

