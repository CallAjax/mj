import React from 'react'
import { withStyles } from 'material-ui/styles'
import Paper from 'material-ui/Paper'
import Typography from 'material-ui/Typography'
import message from 'config/message'




const styles = theme => ({
    root: theme.mixins.gutters({
        paddingTop: 16,
        paddingBottom: 16,
        marginTop: theme.spacing.unit ,
    }),
})

const Message = (props) => {
    const {classes} = props

    return (
        <Paper className={classes.root} elevation={4}>
            <Typography variant="headline" align="center" component="h3" gutterBottom>
                {message.title}
            </Typography>
            {
                message.list.map((msg, index) => {
                    return (
                        <Typography key={index} variant="subheading" gutterBottom>
                            {`${index+1}、 ${msg}`}
                        </Typography>
                    )
                })
            }
        </Paper>
    )
}

export default withStyles(styles)(Message)