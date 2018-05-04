import {
    UPDATE_ROUTES,
} from 'actionTypes'


export const updateRoutes = (routes) => {
    return { type: UPDATE_ROUTES, routes}
}