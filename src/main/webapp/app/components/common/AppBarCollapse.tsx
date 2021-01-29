import React, {Component} from "react"
import {Icon, MenuItem} from "@material-ui/core"
import { WithStyles, withStyles, Theme, makeStyles } from "@material-ui/core/styles"
import ButtonAppBarCollapse from "./ButtonAppBarCollapse"
import {
    NavLink
} from 'react-router-dom'
import {inject, observer} from "mobx-react"
import {UserStore} from '../../stores/UserStore'
import {CartStore} from "../../stores/CartStore"
import RouteModel from "../../models/RouteModel"

interface IProps extends WithStyles<typeof styles> {
    routes: Array<RouteModel>,
    userStore: UserStore,
    cartStore: CartStore
}

interface IState {
}

const styles = ((theme: Theme) => ({
    root: {
        position: "absolute",
        right: 0,
    },
    buttonBar: {
        [theme.breakpoints.down("xs")]: {
            display: "none"
        },
        margin: "10px",
        paddingLeft: "16px",
        right: "10px",
        position: "relative",
        width: "100%",
        background: "transparent",
        display: "inline"
    },
    buttonBarItem: {
        webkitTransition: 'background-color .3s',
        transition: 'background-color .3s',
        fontSize: '1rem',
        color: '#fff',
        padding: '15px',
        cursor: 'pointer',
        textDecoration: 'none'
    },
    buttonBarItemActive: {
        backgroundColor: '#ea454b',
    },
    mobileButtonBarItem: {
        textDecoration: 'none',
    },
    mobileButtonBarItemActive: {
        backgroundColor: '#ccc',
    },
    shoppingCart: {
        marginRight: '10px'
    }
}))

@inject('cartStore', 'userStore')
@observer
class AppBarCollapse extends Component<IProps, IState> {

    constructor(props) {
        super(props)
    }

    // обработчик события "клик по иконе корзины" - переключатель видимости корзины
    handleCartIconClick = (e) => {
        if (this.props.cartStore.cartShown) {
            this.props.cartStore.setCartVisibility(false)
        } else {
            this.props.cartStore.setCartVisibility(true)
        }
    }

    render() {
        const { classes } = this.props
        const { routes } = this.props
        return (
            <div className={classes.root}>
                <ButtonAppBarCollapse>
                {routes.map(route => {
                    /* выводим на панель навигации ссылки только на те разделы сайта,
                    * в имени модели роута которых не встречается шаблон:
                    * слово Dashboard + один или более символов латинского алфавита
                    * в верхнем или нижнем регистре
                    * (так пользователь администратор увидит ссылку на главную страницу админпанели,
                    * но не увидит лишние ссылки на разделы админпанели) */
                    if(!/^Dashboard[A-z]+$/.test(route.name)) {
                        return <MenuItem>
                            <NavLink
                                key={route.path}
                                as={NavLink}
                                to={route.path}
                                className={classes.mobileButtonBarItem}
                                activeClassName={classes.mobileButtonBarItemActive}
                                exact>
                                {route.name}
                            </NavLink>
                        </MenuItem>
                    } else {
                        return ''
                    }
                })}
                </ButtonAppBarCollapse>
                <div className={classes.buttonBar} id="appbar-collapse">
                    {routes.map(route => {
                        /* выводим на панель навигации ссылки только на те разделы сайта,
                        * в имени модели роута которых не встречается шаблон:
                        * слово Dashboard + один или более символов латинского алфавита
                        * в верхнем или нижнем регистре
                        * (так пользователь администратор увилит ссылку на главную страницу админпанели,
                        * но не увидит лишние ссылки на разделы админпанели)*/
                        if(!/^Dashboard[A-z]+$/.test(route.name)) {
                            return <NavLink
                                    key={route.path}
                                    as={NavLink}
                                    to={route.path}
                                    // можно указать в двойных кавычках имя
                                    // класса стиля, описанного в css
                                    className={classes.buttonBarItem}
                                    // , а в данном случае создается экранирование
                                    // фигурными скобками, и внутри него
                                    // указывается имя класса стиля,
                                    // определенного в константе styles
                                    activeClassName={classes.buttonBarItemActive}
                                    exact>
                                    {route.name}
                                </NavLink>
                        } else {
                            return ''
                        }
                    })}
                </div>
                <div className={classes.shoppingCart} style={{display: this.props.userStore.user ? 'inline' : 'none' }}>
                    <Icon
                        onClick={this.handleCartIconClick}
                    >
                        shopping_cart
                    </Icon> {this.props.cartStore.cartItemsCount} ({this.props.cartStore.cartItemsTotalPrice})
                </div>
            </div>
        )
    }
}

export default withStyles(styles)(AppBarCollapse)