package com.dsoft.liverpooldirectory2.custom

import android.content.Context
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import com.dsoft.liverpooldirectory2.R
import com.google.android.material.button.MaterialButton

class MainButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialButton(ContextThemeWrapper(context, R.style.ButtonStyle), attrs, defStyleAttr)