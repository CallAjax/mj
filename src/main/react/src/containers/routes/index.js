import React,{Component} from 'react'
import { BrowserRouter, Route, Switch, Redirect } from 'react-router-dom'
import { connect } from 'react-redux'
import Loading from 'components/Loading'
import localforage from 'localforage'
import { validateRoutes, updateRoutes } from 'actions'
import Login from 'containers/login'
import Error from 'components/Error'
import Home from 'containers/home'
import {put} from "redux-saga/es/effects";
import Immutable from "immutable";


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