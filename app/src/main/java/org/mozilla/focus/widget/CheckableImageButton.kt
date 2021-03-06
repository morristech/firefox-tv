/* -*- Mode: Java; c-basic-offset: 4; tab-width: 4; indent-tabs-mode: nil; -*-
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.focus.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Checkable
import android.widget.ImageButton

private val checkedStateSet = intArrayOf(android.R.attr.state_checked)

/**
 * ImageButton that implements Checkable.
 *
 * NB: We need a checkable button with a centered drawable for the overlay.
 * CheckBox uses a non-centered drawable, and we can't use ToggleButton
 * because it uses android:background to set the image, which we need for button state,
 * so we make a custom button.
 *
 * We need to override onCreateDrawableState (see CompoundButton source) to add
 * the checked state to the attrs.
 */
class CheckableImageButton @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : ImageButton(context, attrs, defStyle), Checkable {

    private var internalIsChecked = false
        set(value) {
            field = value
            refreshDrawableState()
        }

    override fun isChecked() = internalIsChecked

    override fun toggle() {
        isChecked = !internalIsChecked
    }

    override fun setChecked(checked: Boolean) {
        internalIsChecked = checked
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (internalIsChecked) {
            View.mergeDrawableStates(drawableState, checkedStateSet)
        }
        return drawableState
    }

    override fun performClick(): Boolean {
        toggle()
        return super.performClick()
    }
}
