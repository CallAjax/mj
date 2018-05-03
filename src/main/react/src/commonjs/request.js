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
    if(response.data.status === null) {
        if(response.headers.hasOwnProperty('access_token')) {
            localforage.setItem('Authorization','Bearer ' + response.headers['access_token'])
            localforage.setItem('routes',Immutable.fromJS(response.data['tokenInfo']['routes']))
            localforage.setItem('menu',Immutable.fromJS(response.data['tokenInfo']['menu']))
        }
    } else {
        window.location.replace('/')
    }
    return response;
}, function (error) {
    return Promise.reject(error);
});

export const post = (url, data) => axios.post(url, data)