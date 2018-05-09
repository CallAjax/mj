import React,{Component} from 'react'
import { BrowserRouter, Route, Switch, Redirect } from 'react-router-dom'
import { connect } from 'react-redux'
import Loading from 'components/Loading'
import localforage from 'localforage'
import { validateRoutes, updateRoutes } from 'actions'
import Login from 'containers/login'
import Home from 'containers/home'
import Immutable from "immutable";
import { post } from 'commonjs/request'


class Routes extends Component {
    constructor(props, context) {
        super(props, context)
    }


    componentWillMount() {
       setTimeout(() => {
           localforage.getItem('Authorization').then(v => {
               if(v === null) {
                   const routes = Immutable.fromJS({'/login':{}})
                   this.props.updateRoutes(routes)
                   if(this.props.location.pathname !== '/login') {
                       this.props.history.replace('/login')
                   }
               } else {
                   post('/auth/token').then(() => {
                       console.log(1111111)
                   })
               }
           })
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
        validateRoutes: (...args) => dispatch(validateRoutes(...args)),
        updateRoutes: (...args) => dispatch(updateRoutes(...args)),
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Routes)