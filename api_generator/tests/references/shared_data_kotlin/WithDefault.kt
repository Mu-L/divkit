// Generated code. Do not modify.

package com.yandex.div.reference

import org.json.JSONArray
import org.json.JSONObject

class WithDefault() : Hashable {

    private var _hash: Int? = null 

    override fun hash(): Int {
        _hash?.let {
            return it
        }
        val hash = this::class.hashCode()
        _hash = hash
        return hash
    }

    fun equals(other: WithDefault?, resolver: ExpressionResolver, otherResolver: ExpressionResolver): Boolean {
        return other != null
    }

    fun copy() = WithDefault()

    companion object {
        const val TYPE = "default"
    }
}
