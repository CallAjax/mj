/**
 * 客户端判断
 * @returns {boolean} true:pc,false:mobile
 */
export const isPc = () => {
    const userAgent = navigator.userAgent
    const agents = ['Android', 'iPhone','SymbianOS', 'Windows Phone','iPad', 'iPod']
    if(agents.find((str) => userAgent.includes(str)) === undefined) {
        return true
    } else {
        return false
    }
}

/**
 * 节流
 * @param method
 * @param time
 */
export const throttle = (method, time) => {
    clearTimeout(method.timer)
    method.timer = setTimeout(() => method(),time)
}