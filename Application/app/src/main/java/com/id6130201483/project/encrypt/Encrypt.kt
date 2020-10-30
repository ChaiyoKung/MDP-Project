package com.id6130201483.project.encrypt

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.Base64

interface Encrypt {
    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        fun encryptString(s: String): String {
            val plaintext: ByteArray = s.encodeToByteArray()
            val encodedString: String = Base64.getEncoder().encodeToString(plaintext)

            return encodedString
        }
    }
}
