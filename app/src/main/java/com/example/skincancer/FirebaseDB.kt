package com.example.skincancer

import com.google.firebase.database.*
import kotlin.collections.ArrayList

class FirebaseDB() {

    var database: DatabaseReference? = null

    companion object {
        private var instance: FirebaseDB? = null
        fun getInstance(): FirebaseDB {
            return instance ?: FirebaseDB()
        }
    }

    init {
        connectByURL("https://patient-161e1-default-rtdb.europe-west1.firebasedatabase.app/")
    }

    fun connectByURL(url: String) {
        database = FirebaseDatabase.getInstance(url).reference
        if (database == null) {
            return
        }
        val skinCancerListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get instances from the cloud database
                val skinCancers = dataSnapshot.value as HashMap<String, Object>?
                if (skinCancers != null) {
                    val keys = skinCancers.keys
                    for (key in keys) {
                        val x = skinCancers[key]
                        SkinCancerDAO.parseRaw(x)
                    }
                    // Delete local objects which are not in the cloud:
                    val locals = ArrayList<SkinCancer>()
                    locals.addAll(SkinCancer.SkinCancerAllInstances)
                    for (x in locals) {
                        if (keys.contains(x.id)) {
                            //check
                        } else {
                            SkinCancer.killSkinCancer(x.id)
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            	//onCancelled
            }
        }
        database!!.child("skinCancers").addValueEventListener(skinCancerListener)
    }

    fun persistSkinCancer(ex: SkinCancer) {
        val evo = SkinCancerVO(ex)
        val key = evo.id
        if (database == null) {
            return
        }
        database!!.child("skinCancers").child(key).setValue(evo)
    }

    fun deleteSkinCancer(ex: SkinCancer) {
        val key: String = ex.id
        if (database == null) {
            return
        }
        database!!.child("skinCancers").child(key).removeValue()
    }
}
