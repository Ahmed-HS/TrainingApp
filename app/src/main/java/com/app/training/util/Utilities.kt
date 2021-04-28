package com.app.training.util

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Patterns
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import com.bumptech.glide.Glide
import com.app.training.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.snackbar.Snackbar


/** dp size to px size. */
fun Context.dp2Px(dp: Int): Int {
    val scale = resources.displayMetrics.density
    return (dp * scale).toInt()
}

@ColorInt
fun Context.getColorFromAttr(
        @AttrRes attrColor: Int,
        typedValue: TypedValue = TypedValue(),
        resolveRefs: Boolean = true
): Int {
    theme.resolveAttribute(attrColor, typedValue, resolveRefs)
    return typedValue.data
}

fun ChipGroup.onAllChecked(operation: (chip: Chip) -> Unit){
    checkedChipIds.forEach { id ->
        val chip = this.findViewById<Chip>(id)
        if(chip.isChecked)
        {
            operation(chip)
        }
    }
}

fun Activity.setStatusBarColor(viewToMatch: View? = null){
    val window = window
    val viewColor = viewToMatch?.background as? ColorDrawable
    if(viewColor != null)
    {
        window.statusBarColor = viewColor.color
    }
    else
    {
        window.statusBarColor = getColorFromAttr(android.R.attr.windowBackground)
    }
}

fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
fun CharSequence?.isValidPhone() = !isNullOrEmpty() && Patterns.PHONE.matcher(this).matches()

fun View.showErrorSnackBar(message:String)
{
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
        .setBackgroundTint(context.getColor(R.color.Red_200))
        .show()
}


fun ContentResolver.getFileName(fileUri: Uri): String {
    var name = ""
    val returnCursor = this.query(fileUri, null, null, null, null)
    if (returnCursor != null) {
        val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        name = returnCursor.getString(nameIndex)
        returnCursor.close()
    }
    return name
}

fun ImageView.loadURL(url:String)
{
    Glide.with(this)
            .load(url)
            .into(this)

}

fun Context.callPhoneNumber(phone: String)
{
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:${phone}")
    }
    if (intent.resolveActivity(this.packageManager) != null) {
        startActivity(intent)
    }
}

