import {action, computed, makeObservable, observable} from 'mobx'
import commonStore from './CommonStore'
import CartItemModel from "app/models/CartItemModel"

class CartStore {

    private HTTP_STATUS_OK: number = 200
    private HTTP_STATUS_CREATED: number = 201

    // список всех элементов корзины покупателя
    @observable cartItems: Array<CartItemModel> = []
    // отображать ли корзину?
    @observable cartShown: boolean = false

    constructor() {
        makeObservable(this)
    }

    @computed get cartItemsCount () {
        return this.cartItems
            .map(cartItem => cartItem.quantity)
            .reduce((previousValue, currentValue) => previousValue + currentValue, 0)
    }

    @computed get cartItemsTotalPrice () {
        return this.cartItems
            .map(cartItem => cartItem.price * cartItem.quantity)
            .reduce((previousValue, currentValue) => previousValue + currentValue, 0)
            .toFixed(2)
    }

    @action setCartVisibility (open: boolean) {
        this.cartShown = open
    }

    @action fetchCartItems () {
        commonStore.clearError()
        commonStore.setLoading(true)
        fetch(`${commonStore.basename}/api/cart`, {
            method: 'GET'
        }).then((response) => {
            return response.json()
        }).then(responseModel => {
            if (responseModel) {
                if (responseModel.status === 'success') {
                    this.cartItems =
                        JSON.parse(
                            decodeURIComponent(
                                JSON.stringify(responseModel.data)
                                    .replace(/(%2E)/ig, '%20')
                            )
                        )
                } else if (responseModel.status === 'fail') {
                    commonStore.setError(responseModel.message)
                }
            }
        }).catch((error) => {
            commonStore.setError(error.message)
            throw error
        }).finally(action(() => {
            commonStore.setLoading(false)
        }))
    }

    // действие добавление товара в корзину
    // с указанием ИД товара и функции обратного вызова,
    // которую нужно вызвать после ответа сервера
    @action addToCart(productId: number, notifySuccess: () => void) {
        commonStore.clearError()
        commonStore.setLoading(true)
        fetch(`${commonStore.basename}/api/cart/` + productId,{
            method: 'POST'
        }).then((response) => {
            return response.json()
        }).then(responseModel => {
            if (responseModel) {
                if (responseModel.status === 'success') {
                    // запрос на получение всех элементов с сервера
                    this.fetchCartItems()
                    // уведомление пользователя об успехе
                    notifySuccess()
                } else if (responseModel.status === 'fail') {
                    commonStore.setError(responseModel.message)
                }
            }
        }).catch((error) => {
            commonStore.setError(error.message)
            throw error
        }).finally(action(() => {
            commonStore.setLoading(false)
        }))
    }

    @action subtractFromCart(productId: number, notifySuccess: () => void) {
        commonStore.clearError()
        commonStore.setLoading(true)
        fetch(`${commonStore.basename}/api/cart/` + productId,{
            method: 'PATCH'
        }).then((response) => {
            return response.json()
        }).then(responseModel => {
            if (responseModel) {
                if (responseModel.status === 'success') {
                    // запрос на получение всех элементов с сервера
                    this.fetchCartItems()
                    // уведомление пользователя об успехе
                    notifySuccess()
                } else if (responseModel.status === 'fail') {
                    commonStore.setError(responseModel.message)
                }
            }
        }).catch((error) => {
            commonStore.setError(error.message)
            throw error
        }).finally(action(() => {
            commonStore.setLoading(false)
        }))
    }

    /* @action getPurchaseButton(givePurchaseButtonHtml: (htmlText: string) => void) {
        commonStore.clearError()
        commonStore.setLoading(true)
        fetch("/simplespa/api/cart/pay",{
            method: 'GET',
            mode: 'no-cors'
        }).then((response) => {
            return response.text()
        }).then(response => {
            if (response) {
                console.log(response)
                givePurchaseButtonHtml(response)
            }
        }).catch((error) => {
            commonStore.setError(error.message)
            throw error
        }).finally(action(() => {
            commonStore.setLoading(false)
        }))
    } */
}
export {CartStore}
export default new CartStore()