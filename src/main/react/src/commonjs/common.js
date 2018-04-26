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