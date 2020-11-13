package com.gkgio.borsch_cooker.view

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.PrecomputedTextCompat
import androidx.recyclerview.widget.RecyclerView

open class AbstractHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    @JvmField protected var mItem: T? = null

    /**
     * Bind data on View
     */
    fun bindHolder(item: Any?) {
        if (item == null) {
            bindFail()
        } else {
            val temp: T = item as T
            beforeBind(temp)
            mItem = temp
            bind(temp)
        }
    }

    /**
     * Bind data on a View with payloads
     */
    open fun bindHolder(item: Any?, payloads: List<Any?>) {
        mItem = item as T?
        bindPayloads(payloads)
    }

    open fun beforeBind(item: T) {
    }

    open fun bindFail() {
    }

    open fun bind(item: T) {
    }

    open fun bindPayloads(payloads: List<Any?>) {
    }

    /**
     * View attached to Window
     */
    open fun viewAttachedToWindow() {
    }

    /**
     * View detached from Window
     */
    open fun viewDetachedFromWindow() {
    }

    /**
     * View recycled
     */
    open fun viewRecycled() {
    }

    /**
     * If recycling failed (example - because of animation), do we need force recycle?
     * @return true - if need force recycling; false - if not
     */
    open fun viewRecyclerFailed(): Boolean = true


    /*
     * Protected methods
     */
    /**
     * Will get resource as String and then invokes setText()
     * @param view TextView or his extending
     * @param resourceId id of content for TextView
     */
    protected open fun setStringResource(view: TextView, @StringRes resourceId: Int) {
        setText(view, view.context.getString(resourceId))
    }

    /**
     * if view is instance of AppCompatTextView then invokes setTextFuture else just setText
     * @param view TextView or his extending
     * @param text content for TextView
     */
    protected open fun setText(view: TextView, text: CharSequence?) {
        if (text == null) {
            view.text = text
            return
        }

        if (view is AppCompatTextView) {
            val future = PrecomputedTextCompat.getTextFuture(text, view.textMetricsParamsCompat, null)
            view.setTextFuture(future)
        } else {
            view.text = text
        }
    }
}