package com.morganey.actions

import com.intellij.codeInsight.hint.HintManager
import com.intellij.ide.DataManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DataConstants
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.util.TextRange
import com.morganey.ParseTermScala


/**
 * Created by thoma on 30/09/2016.
 */
class ViewRawTermAction : AnAction() {

    override fun actionPerformed(e : AnActionEvent) {
        val editor : Editor? = DataManager.getInstance().dataContext.getData(DataConstants.EDITOR) as Editor?
        val document = editor?.document
        val line = editor?.caretModel?.visualPosition?.getLine()
        val startOffset = document?.getLineStartOffset(line!!)
        val endOffset = document?.getLineEndOffset(line!!)
        val text = document?.getText(TextRange(startOffset!!, endOffset!!))
        val raw : String
        if(ParseTermScala.parse(text) != null) {
            raw = ParseTermScala.parse(text).get().toString()
        }
        else{
            raw = "Invalid Term: " + text
        }
        val hintManager = HintManager.getInstance()
        hintManager.showInformationHint(editor!!, raw)
    }
}
