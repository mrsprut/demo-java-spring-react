import React from 'react'
import {observer} from 'mobx-react'
import commonStore from '../stores/CommonStore'

export default observer(() => {
    setTimeout(
        () => commonStore.setError('Demo error message'),
        5000
    )
    return (
        <>
            <h1>SpringBoot + React = Stock ECommerce</h1>
            <span>Error: {commonStore.error}</span>
        </>
    )
})