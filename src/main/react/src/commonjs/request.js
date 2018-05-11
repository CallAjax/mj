import axios from 'axios'
import store from 'store'

axios.defaults.baseURL = '/service'
axios.defaults.timeout = 30000

axios.interceptors.request.use(function (config) {
    const auth = store.get('Authorization')
    if(auth !== undefined) {
        config.headers['Authorization'] = auth
    }
    return config
}, function (error) {
    return Promise.reject(error);
});


axios.interceptors.response.use(function (response) {
    if(response.data.status === null || response.data.status === undefined) {
        if(response.headers.hasOwnProperty('access_token')) {
            store.set('Authorization','Bearer ' + response.headers['access_token'])
            const auth = {
                'routes': response.data['tokenInfo']['routes'],
                'menu': response.data['tokenInfo']['menu']
            }
            store.set('auth',auth)
            response['auth'] = auth
        }
    } else {
        store.clearAll()
        window.location.replace('/login')
        return Promise.reject('auth error');
    }
    return response;
}, function (error) {
    if(error.toString() === 'Error: Request failed with status code 403') {
        store.clearAll()
        window.location.replace('/login')
    }
    return Promise.reject(error);
});

export const post = (url, data) => axios.post(url, data)