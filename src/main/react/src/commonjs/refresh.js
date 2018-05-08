import React,{Component} from 'react'
import {is} from 'immutable'


export default params => Comp => class extends Component {
    constructor(props, context) {
        super(props, context)
    }

    shouldComponentUpdate(nextProps,nextState) {
        let isChange = false
        for(let p of params) {
            if(!is(this.props[p], nextProps[p])) {
                isChange = true
                break
            }
        }
        return !is(this.props.location.pathname, nextProps.location.pathname) || isChange
    }

    render() {
        return <Comp {...this.props} />
    }
}