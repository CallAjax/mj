import React,{Component} from 'react'
import { connect } from 'react-redux'
import Paper from 'material-ui/Paper'
import TextField from 'material-ui/TextField';
import { withStyles } from 'material-ui/styles'
import {changeFormsState, sagaFormCommit, changeShowState, changeModalState} from 'actions'
import Grid from 'material-ui/Grid'
import Button from 'material-ui/Button'
import { CircularProgress } from 'material-ui/Progress'
import page from 'config/page'
import Show from 'components/Show'
import Modals from 'components/Modals'

const styles = theme => ({
    root: theme.mixins.gutters({
        paddingTop: 16,
        paddingBottom: 16,
        marginTop: theme.spacing.unit ,
    }),
    container: {
        display: 'flex',
        flexWrap: 'wrap',
    },
    textField: {
        marginLeft: theme.spacing.unit,
        marginRight: theme.spacing.unit,
        width: "100%",
    },
})


class Froms extends Component {
    constructor(props, context) {
        super(props, context)

        this.fieldChange = this.fieldChange.bind(this)
        this.onSubmit = this.onSubmit.bind(this)
        this.showClose = this.showClose.bind(this)
        this.closeModal = this.closeModal.bind(this)
    }

    fieldChange(event) {
        this.props.changeFormsState(event.target.value, event.target.id)
    }

    onSubmit() {
        this.props.sagaFormCommit(this.props.match.params.type)
    }

    showClose() {
        this.props.changeShowState({isShow:false, msg: ''})
    }

    closeModal() {
        this.props.changeModalState({isShow:false,msg:'',title:''})
    }

    render() {
        const {classes, forms, match:{params:{type}}} = this.props
        const fields = forms.get('fields')
        const input = page[type].input
        const toModal = {
            title: '返回内容',
            isShow: forms.getIn(['modal','isShow']),
            msg: forms.getIn(['modal','msg']),
        }

        return (
            <div>
                <Paper className={classes.root} elevation={4}>
                    {
                        input.map((v, i) => {
                            return (
                                <TextField required={fields.getIn([v,'required'])} key={i} onChange={this.fieldChange}
                                           id={v}
                                           label={fields.getIn([v,'label'])}
                                           className={classes.textField}
                                           defaultValue={fields.getIn([v,'value'])}
                                           margin="normal"
                                />
                            )
                        })
                    }
                    <form id="form" action={forms.getIn(['formInfo','url']) || ''} method="post">
                        <input type="hidden" name="dom" value={forms.getIn(['formInfo','dom']) || ''} />
                    </form>
                </Paper>
                <Paper className={classes.root} elevation={4}>
                    <Grid container spacing={24}>
                        <Grid item xs={4} />
                        <Grid item xs={4} style={{'textAlign':'center'}}>
                            <Button variant="raised" disabled={forms.getIn(['btnState','button'])} onClick={this.onSubmit}>提交</Button>
                        </Grid>
                        <Grid item xs={4} >
                            <CircularProgress variant={forms.getIn(['btnState','loading'])}/>
                        </Grid>
                    </Grid>
                </Paper>
                <Show isShow={forms.getIn(['show','isShow'])} msg={forms.getIn(['show','msg'])} showClose={this.showClose}/>
                <Modals {...toModal} closeModal={this.closeModal} />
            </div>

        )
    }
}

const mapStateToProps = state => {
    return {
        forms: state.get('forms')
    }
}

const mapDispatchToProps = dispatch => {
    return {
        changeFormsState: (...args) => dispatch(changeFormsState(...args)),
        sagaFormCommit: (...args) => dispatch(sagaFormCommit(...args)),
        changeShowState: (...args) => dispatch(changeShowState(...args)),
        changeModalState: (...args) => dispatch(changeModalState(...args)),
    }
}


export default connect(mapStateToProps,mapDispatchToProps)(withStyles(styles)(Froms))