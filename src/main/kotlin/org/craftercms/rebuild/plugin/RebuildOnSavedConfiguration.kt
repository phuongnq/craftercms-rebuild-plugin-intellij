package org.craftercms.rebuild.plugin

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project

class RebuildOnSavedConfiguration(private val project: Project) : Configurable {
    private var settingsComponent: AppSettingsComponent? = null

    override fun apply() {
        RebuildOnSavedState.getService(project).enabled = settingsComponent!!.checkbox.isSelected
        RebuildOnSavedState.getService(project).rebuildUrl = settingsComponent!!.rebuildUrl.text
    }

    override fun createComponent() = AppSettingsComponent(project).also { settingsComponent = it }.panel

    override fun disposeUIResources() {
        super.disposeUIResources()
        settingsComponent = null
    }

    override fun getDisplayName() = "Rebuild CrafterCMS Engine on saved Groovy scripts"

    override fun getPreferredFocusedComponent() = settingsComponent!!.checkbox

    override fun isModified(): Boolean {
        val modifiedCheckbox = settingsComponent!!.checkbox.isSelected != RebuildOnSavedState.getService(project).enabled
        val modifiedUrl = settingsComponent!!.rebuildUrl.text != RebuildOnSavedState.getService(project).rebuildUrl
        return modifiedCheckbox || modifiedUrl
    }

    override fun reset() {
        settingsComponent!!.checkbox.isSelected = RebuildOnSavedState.getService(project).enabled
        settingsComponent!!.rebuildUrl.text = RebuildOnSavedState.getService(project).rebuildUrl
    }
}