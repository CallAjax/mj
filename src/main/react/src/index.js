import React from 'react'
import {render} from 'react-dom'
import { BrowserRouter, Route, Switch } from 'react-router-dom'
import { Provider } from 'react-redux'
import store from './redux/reducers'
import Login from 'containers/login'
import Error from 'components/Error'

render(
    <Provider store={store}>
        <BrowserRouter>
            <Switch>
                <Route exact path="/404" component={Error} />
                <Route path="/" component={Login} />
            </Switch>
        </BrowserRouter>
    </Provider>
    ,document.getElementById('container')
)
