import React from 'react'
import Snackbar from 'material-ui/Snackbar'


const Show = (props) => {

    const { isShow, msg, showClose} = props

    return (
        <div>
            <Snackbar
                anchorOrigin={{ vertical: 'top', horizontal: 'center'}}
                open={isShow}
                onClose={showClose}
                SnackbarContentProps={{
                    'aria-describedby': 'message-id',
                }}
                message={<span id="message-id">{msg}</span>}
            />
        </div>
    )
}



export default Show