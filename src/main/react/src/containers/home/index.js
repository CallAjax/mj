import React,{Component} from 'react'
import Routes from 'containers/home/routes'
import refresh from 'commonjs/refresh'
import { connect } from 'react-redux'
import { withStyles } from 'material-ui/styles'
import Grid from 'material-ui/Grid'
import Top from 'components/Top'
import menu from 'config/menu'
import LeftMenu from 'components/LeftMenu'
import {changeMenuState, changeFormsPath} from 'actions'
import {isPc} from 'commonjs/common'


const styles = (theme) => ({
    root: {
        flexGrow: 1,
    },
})


@refresh(['leftShow'])
class Home extends Component {
    constructor(props, context) {
        super(props, context)

        this.topClick = this.topClick.bind(this)
        this.leftClick = this.leftClick.bind(this)
        this.titleClick = this.titleClick.bind(this)
        this.onCloseHandle = this.onCloseHandle.bind(this)
    }


    topClick(event, index) {
        this.props.changeMenuState(true, index)
    }


    leftClick(event) {
        this.props.changeMenuState(true, this.props.leftShow.get('index'))

        const i = event.target.value
        if(i !== undefined) {
            this.props.history.push(
                menu[this.props.leftShow.get('index')].items[i].to
            )
        }
    }

    onCloseHandle() {
        this.props.changeMenuState(false, this.props.leftShow.get('index'))
    }

    titleClick() {
        this.props.changeMenuState(false, undefined)
        this.props.history.push('/')
    }

    /**将form表单数据清空，恢复默认值**/
    componentWillReceiveProps(nextProps) {
        this.props.changeFormsPath(nextProps.location.pathname)
    }

    componentWillMount() {
        if(!isPc()) {
            menu[0].items.splice(1,1)
        }
    }

    render() {
        const { classes, leftShow} = this.props
        const show = leftShow.toJS()

        return (
            <div className={classes.root}>
                <Grid container spacing={8} >
                    <Grid item xs={12}>
                        <Top onClickHandle={this.topClick} titleHandle={this.titleClick} index={show.index} list={menu}/>
                    </Grid>
                    <Grid item xs={12}>
                        <Routes />
                    </Grid>
                </Grid>
                <LeftMenu isShow={show.isShow} onClickHandle={this.leftClick} onCloseHandle={this.onCloseHandle} list={menu} index={show.index}/>
            </div>
        )
    }
}
const mapStateToProps = state => {
    return {
        leftShow: state.getIn(['home','leftShow']),
    }
}

const mapDispatchToProps = dispatch => {
    return {
        changeMenuState: (...args) => dispatch(changeMenuState(...args)),
        changeFormsPath: (...args) => dispatch(changeFormsPath(...args)),
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(withStyles(styles)(Home))