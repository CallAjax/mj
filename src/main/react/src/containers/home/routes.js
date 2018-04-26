// import React from 'react'
// import Bundle from 'components/BundleImport'
// import { Route, Switch, Redirect } from 'react-router-dom'
// import {isPc} from 'commonjs/common'
// import page from 'config/page'
//
//
// const Message = props => (<Bundle load={() => import('components/Message')}>{Comp => <Comp {...props}/>}</Bundle>)
// const Forms = props => (<Bundle load={() => import('containers/forms')}>{Comp => <Comp {...props}/>}</Bundle>)
//
// const pages = Object.keys(page)
//
// if(!isPc()) {
//     pages.findIndex((v, i, o) => {
//         if(v === 'corpor') {
//             o.splice(i, 1)
//         }
//     })
// }
//
// const Routes = () => {
//
//     return (
//         <Switch>
//             <Route exact path="/" component={Message} />
//             <Route exact path={`/:type(${pages.join('\|')})`} component={Forms} />
//             <Route render={() => <Redirect to="/404"/>} />
//         </Switch>
//     )
// }
//
// export default Routes