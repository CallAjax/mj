import {
    SAGA_VALIDATE_ROUTES,
    UPDATE_ROUTES,
} from 'actionTypes'


export const validateRoutes = () => {
    return { type: SAGA_VALIDATE_ROUTES }
}

export const updateRoutes = (routes) => {
    return { type: UPDATE_ROUTES, routes}
}