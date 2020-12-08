import {observable} from 'mobx'
import Home from '../components/pages/Home'
import Shopping from '../components/pages/Shopping'

class RouterStore {

    // список моделей роутов для гостя
    private anonymousRoutes: Array<object> = [
        { path: '/', name: 'Home', Component: Home },
        { path: '/shopping', name: 'Shopping', Component: Shopping },
        { path: '/about', name: 'Shopping', Component: Shopping }
    ]

    // наблюдаемый текущий список роутов
    // (по умолчнию - для гостя)
    @observable routes: Array<object> = this.anonymousRoutes
}

export {RouterStore}
export default new RouterStore()