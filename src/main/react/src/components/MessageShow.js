import React from 'react'
import Fade from 'material-ui/transitions/Fade'

const MessageShow = (props) => {

    const { msg, msgShow } = props

    return (
        <div>
            <Fade in={msgShow}>
                <span style={{color: 'red'}}>{msg}</span>
            </Fade>
        </div>
    )
}



export default MessageShow