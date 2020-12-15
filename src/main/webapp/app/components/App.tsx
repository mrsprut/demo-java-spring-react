import React, {Component} from 'react'
import {
    Router,
    Route
} from 'react-router-dom'
import {CommonStore} from 'app/stores/CommonStore'
import {RouterStore} from 'app/stores/RouterStore'
import history from '../history'
import {Container, withStyles, WithStyles} from '@material-ui/core'
import {inject, observer} from 'mobx-react'
import {CSSTransition} from 'react-transition-group'

interface IProps extends WithStyles<typeof styles> {
    commonStore: CommonStore,
    routerStore: RouterStore
}

interface IState {}

// получаем готовые стили темы material-ui
const styles = theme =>
    ({
        // объявление пользовательского класса стиля
        // (для корневого компонента разметки текущего компонента)
        root: {
            // атрибут класса стиля
            flexGrow: 1,
        },
        container: {
            maxWidth: '970px',
            '& .page' : {
                position: 'static'
            }
        },
        menuButton: {
            marginRight: theme.spacing(2),
        },
        title: {
            flexGrow: 1,
        },
        navBar: {
            color: '#fff',
            backgroundColor: '#ee6e73',
        }
    })

@inject('routerStore', 'commonStore')
@observer
class App extends Component<IProps, IState> {
    constructor(props) {
        super(props)
    }
    render () {
        const { routes } = this.props.routerStore
        const { classes } = this.props
        return (
            <Router history={history}>
                <div className={classes.root}>
                    <Container maxWidth="sm" className={classes.container}>
                        {routes.map(({ path, Component }) => (
                            <Route key={path} exact path={path}>
                                {({ match }) => (
                                    <CSSTransition
                                        in={match != null}
                                        timeout={300}
                                        classNames='page'
                                        unmountOnExit
                                    >
                                        <div className='page'>
                                            <Component />
                                        </div>
                                    </CSSTransition>
                                )}
                            </Route>
                        ))}
                    </Container>
                </div>
            </Router>
        )
    }
}

export default withStyles(styles)(App)