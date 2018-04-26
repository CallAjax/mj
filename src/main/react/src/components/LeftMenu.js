import React from 'react'
import PropTypes from 'prop-types';
import { withStyles } from 'material-ui/styles'
import Drawer from 'material-ui/Drawer'
import { MenuItem, MenuList } from 'material-ui/Menu';
import Divider from 'material-ui/Divider'

const styles = {
    list: {
        width: 120,
    },
}

const LeftMenu = (props) => {
    const {classes, onClickHandle, onCloseHandle, isShow, list, index = 0} = props
    return (
        <div>
            <Drawer open={isShow} onClick={onCloseHandle}>
                <div role="button">
                    <div className={classes.list}>
                        <MenuList role="menu" >
                            {
                                list[index].items.map((item, index) =>
                                        <div key={index+1}>
                                            <MenuItem key={index+2} value={index} onClick={onClickHandle}>{item.name}</MenuItem>
                                            <Divider key={index+3}/>
                                        </div>
                                )
                            }
                        </MenuList>
                    </div>
                </div>
            </Drawer>
        </div>
    )
}

LeftMenu.propTypes = {
    onClickHandle: PropTypes.func.isRequired,
    onCloseHandle: PropTypes.func.isRequired,
}




export default withStyles(styles)(LeftMenu)