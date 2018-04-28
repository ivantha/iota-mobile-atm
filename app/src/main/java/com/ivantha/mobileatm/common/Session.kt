package com.ivantha.mobileatm.common

import android.graphics.Color
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.ivantha.mobileatm.model.Deal
import com.ivantha.mobileatm.model.User


object Session {
    var firebaseUser: FirebaseUser? = null
    var currentUser: User? = null
    var deals: HashMap<String, Deal> = HashMap()
    val dealColors: ArrayList<Int> = ArrayList()

    private var db = FirebaseDatabase.getInstance().reference

    init {
        // Initialize firebaseUser
        firebaseUser = FirebaseAuth.getInstance().currentUser

        // Initialize currentUser
        db.child("users").child(firebaseUser!!.uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                currentUser = dataSnapshot.getValue(User::class.java)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                println("--------------------------------------------------------------------------")
            }
        })

        // Initialize deals
        db.child("deals").addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                deals[dataSnapshot.key] = dataSnapshot.getValue<Deal>(Deal::class.java)!!
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String) {
                deals[dataSnapshot.key] = dataSnapshot.getValue<Deal>(Deal::class.java)!!
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                deals.remove(dataSnapshot.key)
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String) {
                deals[dataSnapshot.key] = dataSnapshot.getValue<Deal>(Deal::class.java)!!
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Failed to read value
            }
        })

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