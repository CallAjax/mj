import React,{Component} from 'react'
import { BrowserRouter, Route, Switch } from 'react-router-dom'
import { connect } from 'react-redux'
import Login from 'containers/login'
import Error from 'components/Error'
import Home from 'containers/home'



class Routes extends Component {
    constructor(props, context) {
        super(props, context)
    }




    render() {
        return (
            <Switch>
                <Route exact path="/404" component={Error} />
                <Route exact path="/" component={Login} />
            </Switch>
        )
    }
}

export default connect()(Routes)