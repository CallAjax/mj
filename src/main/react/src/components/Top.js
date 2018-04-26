import React from 'react'
import { withStyles } from 'material-ui/styles'
import AppBar from 'material-ui/AppBar'
import Toolbar from 'material-ui/Toolbar'
import Typography from 'material-ui/Typography'
import BottomNavigation, { BottomNavigationAction } from 'material-ui/BottomNavigation'
import PropTypes from 'prop-types'

const styles = {
    root: {
        flexGrow: 1,
    },
    flex: {
        flex: 1,
    },
    BottomNavigation: {
        width: 250,
    },
}

const Top = (props) => {
    const {classes, list, index, onClickHandle, titleHandle} = props

    return (
        <div className={classes.root}>
            <AppBar position="static" color="inherit">
                <Toolbar>
                    <Typography variant="title" color="inherit" noWrap onClick={titleHandle} style={{"cursor": "pointer"}}>
                        demopay
                    </Typography>
                    <BottomNavigation  showLabels value={index} onChange={onClickHandle} className={classes.BottomNavigation}>
                        {
                            list.map((value, index) => {
                                return (
                                    <BottomNavigationAction key={index} label={value.name}  />
                                )
                            })
                        }
                    </BottomNavigation>
                </Toolbar>
            </AppBar>
        </div>
    )
}

Top.propTypes = {
    list: PropTypes.array.isRequired,
    onClickHandle: PropTypes.func.isRequired,
    titleHandle: PropTypes.func.isRequired,
}

export default withStyles(styles)(Top)