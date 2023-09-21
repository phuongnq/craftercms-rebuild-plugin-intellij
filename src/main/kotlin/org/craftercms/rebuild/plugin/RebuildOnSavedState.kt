package org.craftercms.rebuild.plugin

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.project.Project
import com.intellij.util.xmlb.XmlSerializerUtil

@State(
    name = "org.craftercms.rebuild.plugin.RebuildOnSavedState",
    storages = [
        Storage("RebuildOnSavedState.xml")
    ]
)
class RebuildOnSavedState : PersistentStateComponent<RebuildOnSavedState> {
    var enabled = false
    var rebuildUrl = "http://localhost:8080/api/1/site/context/rebuild.json?token=defaultManagementToken&crafterSite=default"

    override fun getState() = this

    override fun loadState(state: RebuildOnSavedState) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        fun getService(project: Project): RebuildOnSavedState = project.getService(RebuildOnSavedState::class.java)
    }
}