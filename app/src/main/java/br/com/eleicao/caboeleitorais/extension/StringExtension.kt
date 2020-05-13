package br.com.eleicao.caboeleitorais.extension

import android.util.Base64
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.text.Normalizer

private val REGEX_UNACCENT = "\\p{InCombiningDiacriticalMarks}+".toRegex()

fun String.unaccent(): String {
    val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
    return REGEX_UNACCENT.replace(temp, "")
}

fun String.JWTDecode(propertie: String) =
    try {
        val split = this.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        JSONObject(split[1].getJson()).getString(propertie)
    } catch (e: UnsupportedEncodingException) {
        ""
    }

fun String.getJson() = String(Base64.decode(this, Base64.URL_SAFE), Charset.forName( "UTF-8" ))
