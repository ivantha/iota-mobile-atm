package com.ivantha.mobileatm.common

import android.graphics.Color
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.gson.*
import com.ivantha.mobileatm.model.Deal
import com.ivantha.mobileatm.model.User
import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat

/**
 * Contains the global variable of the current session of the app
 */
object Session {

    // User
    var currentUser: User? = null

    // JSON parser
    var gson: Gson? = null

    // Deals
    var deals: HashMap<String, Deal> = HashMap()
    val dealColors: ArrayList<Int> = ArrayList()

    /**
     * Initialize the session variable and functions
     */
    init {
        // Add deal colors
        dealColors.add(Color.parseColor("#e53935"))
        dealColors.add(Color.parseColor("#1E88E5"))
        dealColors.add(Color.parseColor("#43A047"))
        dealColors.add(Color.parseColor("#546E7A"))
        dealColors.add(Color.parseColor("#8E24AA"))
        dealColors.add(Color.parseColor("#00ACC1"))
        dealColors.add(Color.parseColor("#F4511E"))
        dealColors.add(Color.parseColor("#3949AB"))
    }
}
