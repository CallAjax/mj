import React,{Component} from 'react'
import { Route, Switch, Redirect } from 'react-router-dom'
import { connect } from 'react-redux'
import Loading from 'components/Loading'
import { getRoutes, updateRoutes } from 'actions'
import Login from 'containers/login'
import Home from 'containers/home'
import refresh from 'commonjs/refresh'

@refresh(['auth'])
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
        if(this.props.auth.get('routes').size === 0) {
            return <Loading />
        } else {
            return (
                <Switch>
                    <Route exact path='/' component={Loading}/>
                    {this.props.auth.get('routes').has('/login')?<Route exact path='/login' component={Login}/> : ''}
                    {this.props.auth.get('routes').has('/home')?<Route exact path='/home' component={Home}/> : ''}
                    <Route render={() => <Redirect to="/error"/>} />
                </Switch>
            )
        }
    }
}

const mapStateToProps = state => {
    return {
        auth: state.get('auth'),
    }
}

const mapDispatchToProps = dispatch => {
    return {
        getRoutes: (...args) => dispatch(getRoutes(...args)),
        updateRoutes: (...args) => dispatch(updateRoutes(...args)),
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Routes)