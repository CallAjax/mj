import React,{Component} from 'react'
import { Route, Switch, Redirect } from 'react-router-dom'
import { connect } from 'react-redux'



class Home extends Component {
    constructor(props, context) {
        super(props, context)
    }




    render() {
        return <h1>home</h1>
    }
}

const mapStateToProps = state => {
    return {
        auth: state.get('auth'),
    }
}

export default connect(mapStateToProps)(Home)