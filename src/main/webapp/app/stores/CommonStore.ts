import {action, makeObservable, observable} from 'mobx'

class CommonStore {

    @observable loading: boolean = false
    @observable error: string = null
    @observable basename: string = '/springreact'

    constructor() {
        makeObservable(this)
    }

    @action setLoading(loading: boolean): void {
        this.loading = loading
    }

    @action setError(error: string): void {
        this.error = error
    }

    @action clearError(): void {
        this.error = null
    }
}
// делаем доступным для импорта из текущего модуля
// сам тип хранилища CommonStore
export {CommonStore}
// делаем доступным для импорта из текущего модуля по умолчанию
// экземпляр общего хранилища типа CommonStore
export default new CommonStore()