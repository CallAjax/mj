import axios from 'axios'
import localforage from 'localforage'
import Immutable from 'immutable'

axios.defaults.baseURL = '/service'
axios.defaults.timeout = 30000

localforage.getItem('Authorization').then(v => {
    axios.interceptors.request.use(function (config) {
        if(v !== null) {
            config.headers['Authorization'] = v
        }
        return config
    }, function (error) {
        return Promise.reject(error);
    });
})


axios.interceptors.response.use(function (response) {
    if(response.data.status === null || response.data.status === undefined) {
        if(response.headers.hasOwnProperty('access_token')) {
            localforage.setItem('Authorization','Bearer ' + response.headers['access_token'])
            const auth = {
                'routes': response.data['tokenInfo']['routes'],
                'menu': response.data['tokenInfo']['menu']
            }
            localforage.setItem('auth',auth)
            response['auth'] = auth
        }
    } else {
        localforage.clear().then(() => window.location.replace('/'))
        return Promise.reject('auth error');
    }
    return response;
}, function (error) {
    if(error.toString() === 'Error: Request failed with status code 403') {
        localforage.clear().then(() => window.location.replace('/'))
    }
    return Promise.reject(error);
});

export const post = (url, data) => axios.post(url, data)