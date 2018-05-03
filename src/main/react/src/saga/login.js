import { select, put,call } from 'redux-saga/effects'
import { post } from 'commonjs/request'
import qs from 'qs'
import {
    CHANGE_LOGIN_FORM,
} from 'actionTypes'
import localforage from 'localforage'
import Immutable from 'immutable'

/**
 * 登陆提交
 * @returns {IterableIterator<*>}
 */
export function* login() {
    let obj = {}
    const login = yield select(v => v.get('login'))
    const params = {
        uid: login.get('name'),
        pwd: login.get('password'),
        code: login.get('code'),
        codeId: login.get('codeId'),
    }


    /**数据校验**/
    if(params.uid === null || params.uid === '') {
        obj = {msgShow: true, msg: '请输入用户名...'}
        yield put({type:CHANGE_LOGIN_FORM,obj})
        return false
    }
    if(params.pwd === null || params.pwd === '') {
        obj = {msgShow: true, msg: '请输入密码...'}
        yield put({type:CHANGE_LOGIN_FORM,obj})
        return false
    }
    if(login.get('codeShow') === ''){
        if(params.code === null || params.code === ''){
            obj = {msgShow: true, msg: '请输入验证码...'}
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

        console.log(result)
        /**判断登陆结果**/
        if(result.data.resCode === 'SUCCESS') {//登陆成功
            //本地存储保存token
            console.log(result.headers['access_token'])

        } else {//登陆失败
            yield call(changeCode)//阻塞调用验证码刷新
            obj = getError(result.data.resMsg,{codeShow:''})
            yield put({type:CHANGE_LOGIN_FORM,obj})
        }
    } catch (e) {
        obj = getError('系统繁忙，请稍后再试...')
        yield put({type:CHANGE_LOGIN_FORM,obj})
    }


}


/**
 * 获取验证码
 * @returns {IterableIterator<*>}
 */
export function* changeCode() {
    let obj = {}
    try {
        const result = yield call(post, '/image')
        const obj = {src: result.data,codeId:result.headers.code_id}
        yield put({type:CHANGE_LOGIN_FORM,obj})
    } catch (e) {
        obj = getError('系统繁忙，请稍后再试...')
        yield put({type:CHANGE_LOGIN_FORM,obj})
    }
}

/**
 * 错误状态处理
 * @param msg
 * @param args
 * @returns {*}
 */
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

