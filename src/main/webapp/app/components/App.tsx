import React, {Component} from 'react'
import {
    Router,
    Route
} from 'react-router-dom'
import {CommonStore} from 'app/stores/CommonStore'
import {RouterStore} from 'app/stores/RouterStore'
import {UserStore} from 'app/stores/UserStore'
import history from '../history'
import {AppBar, Container, Toolbar, Typography, withStyles, WithStyles} from '@material-ui/core'
import {inject, observer} from 'mobx-react'
import {CSSTransition} from 'react-transition-group'
import AppBarCollapse from "../components/common/AppBarCollapse";
import {reaction} from "mobx";
import Modal from "@material-ui/core/Modal";

interface IProps extends WithStyles<typeof styles> {
    commonStore: CommonStore,
    routerStore: RouterStore,
    userStore: UserStore
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
        },
        modal: {
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center'
        },
        modalContent: {
            /* position: 'absolute',
            top: `5%`,
            left: `5%`, */
            /* alignItems: 'center',
            justifyContent: 'center', */
            backgroundColor: theme.palette.background.paper,
            border: '2px solid #000',
            boxShadow: theme.shadows[5],
            padding: theme.spacing(2, 4, 3),
        },
        cartModalContent: {
            backgroundColor: theme.palette.background.paper,
            border: '2px solid #000',
            boxShadow: theme.shadows[5],
            padding: theme.spacing(2, 4, 3),
        },
        closeButton: {
            cursor:'pointer',
            float:'right',
            marginTop: '-80px',
            marginRight: '-25px',
        }
    })

@inject('routerStore', 'commonStore', 'userStore')
@observer
class App extends Component<IProps, IState> {
    constructor(props) {
        super(props)
    }

    // установка обработчика события изменения значения
    // в свойстве userStore.user хранилища -
    // задано первым аргументом функции reaction;
    // второй аргумент - функция, которая будет выполнена
    // в ответ на изменения свойства userStore.user,
    // при этом функция -второй аргумент - получает в качестве своего аргумента
    // данные, которые изменились (новую версию)
    userReaction = reaction(
        () => this.props.userStore.user, // следим за свойством user
        (user) => {
            // при изменении значения свойства user
            if (user) {
                // если user установлен -
                // выполняем переход на раздел 'Главная'
                history.replace('/')
                if (user.roleName === 'ROLE_ADMIN') {
                    // ... и меняем текущий список моделей роутов
                    // - на список моделей роутов для вошедшего пользователя-администратора,
                    // если в модели пользователя, полученной с сервера указано имя роли
                    // ROLE_ADMIN
                    this.props.routerStore.setAdminRoutes()
                } else {
                    // ... и меняем текущий список моделей роутов
                    // - на список моделей роутов для вошедшего пользователя
                    this.props.routerStore.setLoggedRoutes()
                }
            } else {
                // если пользователь не установлен -
                // выполняем переход на раздел 'Вход'
                history.replace('/signin')
                // и меняем текущий список моделей роутов
                // - на список моделей роутов для пользователя-гостя
                this.props.routerStore.setAnonymousRoutes()
            }
        }
    )

    handleErrorModalClose = (e) => {
        this.props.commonStore.setError(null)
    }

    render () {
        const { routes } = this.props.routerStore
        const { classes } = this.props
        return (
            <Router history={history}>
                <div className={classes.root}>
                    <AppBar position='sticky' className={classes.navBar}>
                        <Toolbar>
                            <Typography variant='h6' className={classes.title}>
                                SpringReactSPA
                            </Typography>
                            <AppBarCollapse routes={routes} userStore={this.props.userStore} />
                        </Toolbar>
                    </AppBar>
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
                    <Modal
                        open={ !!this.props.commonStore.error }
                        onClose={ this.handleErrorModalClose }
                        aria-labelledby="simple-modal-title"
                        aria-describedby="simple-modal-description"
                        className={classes.modal}
                    >
                        <div className={classes.modalContent}>
                            {this.props.commonStore.error}
                        </div>
                    </Modal>
                </div>
            </Router>
        )
    }
}

export default withStyles(styles)(App)