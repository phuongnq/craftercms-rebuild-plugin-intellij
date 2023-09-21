package org.craftercms.rebuild.plugin

import com.intellij.ide.actionsOnSave.impl.ActionsOnSaveFileDocumentManagerListener.ActionOnSave
import com.intellij.openapi.components.service
import com.intellij.openapi.editor.Document
import com.intellij.openapi.project.Project
import com.intellij.openapi.fileEditor.FileDocumentManager
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class RebuildOnSavedAction : ActionOnSave() {
    override fun isEnabledForProject(project: Project): Boolean {
        return project.service<RebuildOnSavedState>().enabled
    }

    override fun processDocuments(project: Project, documents: Array<out Document>) {
        if (hasGroovyFiles(documents)) {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(project.service<RebuildOnSavedState>().rebuildUrl)
                .build()
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")
            }
        }
    }

    fun isGroovyFile(document: Document): Boolean {
        val virtualFile = FileDocumentManager.getInstance().getFile(document)
        return virtualFile?.extension?.lowercase() == "groovy"
    }

    fun hasGroovyFiles(documents: Array<out Document>): Boolean {
        for (document in documents) {
            if (isGroovyFile(document)) {
                return true
            }
        }
        return false
    }
}
