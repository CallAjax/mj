import React,{Component} from 'react'
import { withStyles } from 'material-ui/styles'
import Grid from 'material-ui/Grid'
import Paper from 'material-ui/Paper'
import TextField from 'material-ui/TextField'
import Button from 'material-ui/Button'
import Icon from 'material-ui/Icon'
import { connect } from 'react-redux'
import { changeLoginForm, loginCommit, changeCode } from 'actions'
import MessageShow from 'components/MessageShow'
import { CircularProgress } from 'material-ui/Progress'


const styles = theme => ({
    root: {
        flexGrow: 1,
    },
    paper: {
        width: '100%',
        textAlign: 'center',
    },
    textField: {
        marginLeft: theme.spacing.unit,
        marginRight: theme.spacing.unit,
        width: 200,
    },
    button: {
        margin: theme.spacing.unit,
    },
    rightIcon: {
        marginLeft: theme.spacing.unit,
    },
    img: {
        marginBottom: -10,
    },
})

class Login extends Component {
    constructor(props, context) {
        super(props, context)

        this.inputChange = this.inputChange.bind(this)
        this.onCommit = this.onCommit.bind(this)
        this.changeCode = this.changeCode.bind(this)
    }

    componentDidMount() {
        let resize = null
        window.onresize = () => {
            resize = resize ? null : setTimeout(() =>
                document.getElementById('paper').style.height = window.innerHeight + 'px',0)
        }
    }

    inputChange(e) {
        this.props.changeLoginForm({[e.target.id]:e.target.value})
    }

    onCommit() {
        this.props.loginCommit()
    }

    changeCode(e) {
        this.props.changeCode()
    }

    render() {
        const { classes, login } = this.props

        return (
            <div>
                <Grid container className={classes.root}>
                    <Grid item xs={12} >
                        <Paper id='paper' className={classes.paper} style={{height:window.innerHeight}}>
                            <Grid item xs={12} style={{paddingTop:'20%'}}>
                                <Grid item xs={12}>
                                    <TextField
                                        id="name"
                                        label="用户名："
                                        className={classes.textField}
                                        value={login.get('name')}
                                        margin="normal"
                                        onChange={this.inputChange}
                                    />
                                </Grid>
                                <Grid item xs={12}>
                                    <TextField
                                        id="password"
                                        label="密码："
                                        className={classes.textField}
                                        type="password"
                                        value={login.get('password')}
                                        autoComplete="current-password"
                                        margin="normal"
                                        onChange={this.inputChange}
                                    />
                                </Grid>
                                <Grid item xs={12} style={{display:`${login.get('codeShow')}`}}>
                                    <TextField
                                        id="code"
                                        label="验证码："
                                        className={classes.textField}
                                        value={login.get('code')}
                                        margin="normal"
                                        onChange={this.inputChange}
                                        style={{width: 120}}
                                    />
                                    <img className={classes.img} alt="验证码" src={`data:image/jpeg;base64,${login.get('src')}`} onClick={this.changeCode} />
                                </Grid>
                                <Grid item xs={12}>

                                </Grid>
                                <Grid item xs={12}>
                                    <Button className={classes.button} disabled={login.get('disabled')} variant="raised" color="primary"
                                    onClick={this.onCommit}>
                                        Send
                                        <Icon className={classes.rightIcon}>send</Icon>
                                    </Button>
                                </Grid>
                                <MessageShow msgShow={login.get('msgShow')} msg={login.get('msg')}/>
                                <CircularProgress size={25} variant={login.get('loading')}/>
                            </Grid>
                        </Paper>
                    </Grid>
                </Grid>
            </div>
        )
    }
}


const mapStateToProps = state => {
    return {
        login: state.get('login'),
    }
}

const mapDispatchToProps = dispatch => {
    return {
        changeLoginForm: (...args) => dispatch(changeLoginForm(...args)),
        loginCommit: (...args) => dispatch(loginCommit(...args)),
        changeCode: (...args) => dispatch(changeCode(...args)),
    }
}


export default connect(mapStateToProps, mapDispatchToProps)(withStyles(styles)(Login))