// import { select, put,call } from 'redux-saga/effects'
// import { post } from 'commonjs/request'
// import page from 'config/page'
// import { CHANGE_FORMS_BTN_STATE, UPDATE_FORMS_DOM, CHANGE_SHOW_STATE, CHANGE_MODAL_STATE } from 'actionTypes'
// import { isPc } from 'commonjs/common'
// import {is} from 'immutable'
//
// export function* formSend(action) {
//
//     let btnState = {button: true,loading: 'indeterminate'}
//
//     //按钮禁用，开始loading
//     yield put({type:CHANGE_FORMS_BTN_STATE,btnState})
//     //给错误提示，恢复按钮
//     btnState = {button: false,loading: 'determinate'}
//
//     const data = {
//         'pc': isPc(),
//         'type': action.page,
//     }
//
//     //拿store
//     const forms = yield select(v => v.get('forms'))
//     forms.get('fields').forEach((v, k) => data[k] = v.get('value'))
//
//     try {
//         //ajax
//         const result = yield call(post, page[action.page].url, data)
//         const form = {url:result.data.url, xml:result.data.xml}
//         yield put({type:UPDATE_FORMS_DOM,form})
//
//         //部分form表单提交，部分后端请求后返回json
//         if(is(page[action.page].method,'post')) {
//             document.getElementById('form').submit()
//         } else {
//             const modal = {
//                 title: '返回信息:',
//                 isShow: true,
//                 msg: result.data.xml,
//             }
//             yield put({type:CHANGE_FORMS_BTN_STATE,btnState})
//             yield put({type:CHANGE_MODAL_STATE,modal})
//         }
//     } catch(e) {
//         yield put({type:CHANGE_FORMS_BTN_STATE,btnState})
//         const show = {isShow: true, msg: '签名失败，请检查参数...'}
//         yield put({type:CHANGE_SHOW_STATE,show})
//     }
//
// }