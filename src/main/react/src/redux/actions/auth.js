import {
    SAGA_GET_ROUTES,
    UPDATE_ROUTES,
} from 'actionTypes'


export const getRoutes = (props) => {
    return { type: SAGA_GET_ROUTES, props }
}

export const updateRoutes = (obj, name) => {
    return { type: UPDATE_ROUTES, obj, name}
}