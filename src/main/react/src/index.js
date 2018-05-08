import React,{Component} from 'react'
import {render} from 'react-dom'
import { BrowserRouter, Route, Switch } from 'react-router-dom'
import { Provider } from 'react-redux'
import store from './redux/reducers'
import Error from 'components/Error'
import Routes from 'containers/routes'

render(
    <Provider store={store}>
        <BrowserRouter>
            <Switch>
                <Route exact path='/error' component={Error} />
                <Route path='/' component={Routes} />
            </Switch>
        </BrowserRouter>
    </Provider>
    ,document.getElementById('container')
)

