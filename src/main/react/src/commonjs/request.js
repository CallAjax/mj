import axios from 'axios'


axios.defaults.baseURL = '/service'
axios.defaults.timeout = 30000

export const post = (url, data) => axios.post(url, data)