package com.example.vkalbums.utils

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.TextView
import com.example.vkalbums.R

/**
 * Sets a placeholder with an error text
 */
fun vkErrorHandler(
    context: Context,
    errorLabel: TextView,
    code: Int?,
    Ex: Exception
) {
    when (code) {
        ErrorTypes.TOKEN_EXPIRED -> errorLabel.text =
            context.getString(R.string.token_expired_error)
        ErrorTypes.TOO_MANY_REQUESTS -> errorLabel.text =
            context.getString(R.string.too_many_requests_error)
        ErrorTypes.SERVER_ERROR -> errorLabel.text = context.getString(R.string.server_error)
        ErrorTypes.CAPTCHA -> errorLabel.text = context.getString(R.string.captcha_error)
        ErrorTypes.PERMISSION_DENIED -> errorLabel.text =
            context.getString(R.string.permission_denied_error)
        ErrorTypes.BLOCKED -> errorLabel.text = context.getString(R.string.user_blocked_error)
        ErrorTypes.INCORRECT_PARAM -> errorLabel.text =
            context.getString(R.string.incorrect_param_error)
        ErrorTypes.ALBUM_PERMISSION_DENIED -> errorLabel.text =
            context.getString(R.string.album_permission_denied_error)
        ErrorTypes.TOKEN_INVALID -> errorLabel.text =
            context.getString(R.string.token_invalid_error)
        else -> errorLabel.text = context.getString(R.string.unknown_error)
    }
    errorLabel.visibility = View.VISIBLE
    Ex.localizedMessage?.let { Log.e("Error", it) }
}