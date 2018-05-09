import React,{Component} from 'react'
import { BrowserRouter, Route, Switch, Redirect } from 'react-router-dom'
import { connect } from 'react-redux'
import Loading from 'components/Loading'
import { getRoutes, updateRoutes } from 'actions'
import Login from 'containers/login'
import Home from 'containers/home'
import Immutable from "immutable";


class Routes extends Component {
    constructor(props, context) {
        super(props, context)
    }


    componentWillMount() {
        setTimeout(() => {
            this.props.getRoutes(this.props)
        },2000)
    }

    render() {
        if(this.props.routes.size === 1) {
            return <Loading />
        } else {
            return (
                <div>
                    {this.props.routes.has('/login')?<Route path='/login' component={Login}/> : ''}
                    {this.props.routes.has('/home')?<Route path='/login' component={Home}/> : ''}
                </div>
            )
        }
    }
}

const mapStateToProps = state => {
    return {
        routes: state.get('routes'),
        login: state.get('login'),
    }
}

const mapDispatchToProps = dispatch => {
    return {
        getRoutes: (...args) => dispatch(getRoutes(...args)),
        updateRoutes: (...args) => dispatch(updateRoutes(...args)),
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Routes)