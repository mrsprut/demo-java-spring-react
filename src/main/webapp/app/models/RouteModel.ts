export default class RouteModel {
    public path: string
    public name: string
    public Component: (() => JSX.Element) | any
    constructor (path, name, Component) {
        this.path = path
        this.name = name
        this.Component = Component
    }
}