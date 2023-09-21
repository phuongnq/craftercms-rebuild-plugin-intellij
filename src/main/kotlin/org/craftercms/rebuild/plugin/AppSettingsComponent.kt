package org.craftercms.rebuild.plugin

import com.intellij.openapi.project.Project
import com.intellij.ui.components.CheckBox
import com.intellij.ui.dsl.builder.panel
import javax.swing.JLabel
import javax.swing.JTextField

class AppSettingsComponent(project: Project) {
    val checkbox = CheckBox("Rebuild on saved.", RebuildOnSavedState.getService(project).enabled)
    var rebuildUrl = JTextField(RebuildOnSavedState.getService(project).rebuildUrl)
    val panel = panel {
        row {
            cell(checkbox)
        }
        row {
            cell(JLabel("Rebuild URL:"))
        }
        row {
            cell(rebuildUrl)
        }
    }
}