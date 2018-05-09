import {
    SAGA_GET_ROUTES,
    UPDATE_ROUTES,
} from 'actionTypes'


export const getRoutes = (props) => {
    return { type: SAGA_GET_ROUTES, props }
}

export const updateRoutes = (routes) => {
    return { type: UPDATE_ROUTES, routes}
}