import React from 'react'
import { withStyles } from 'material-ui/styles'
import Typography from 'material-ui/Typography'
import Modal from 'material-ui/Modal'

const styles = theme => ({
    paper: {
        position: 'absolute',
        width: theme.spacing.unit * 50,
        backgroundColor: theme.palette.background.paper,
        boxShadow: theme.shadows[5],
        padding: theme.spacing.unit * 4,
    },
});

const getModalStyle = () => {
    const top = 50
    const left = 50

    return {
        top: `${top}%`,
        left: `${left}%`,
        transform: `translate(-${top}%, -${left}%)`,
    };
}

const Modals = (props) => {
    const { classes, title, isShow, msg, closeModal } = props

    return (
        <div>
            <Modal
                aria-labelledby="simple-modal-title"
                aria-describedby="simple-modal-description"
                open={isShow}
                onClose={closeModal}>
                <div style={getModalStyle()} className={classes.paper}>
                    <Typography variant="title" id="modal-title">
                        {title}
                    </Typography>
                    <Typography variant="subheading" id="simple-modal-description">
                        {msg}
                    </Typography>
                </div>
            </Modal>
        </div>
    )
}


export default withStyles(styles)(Modals)
