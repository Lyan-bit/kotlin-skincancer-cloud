package com.example.skincancer

import android.content.Context
import java.util.*
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

class ImageRecognitionBean(c: Context) {
    private var model: ModelFacade = ModelFacade.getInstance(c)

    private var skincancer = ""
	private var instanceSkinCancer: SkinCancer? = null
	
     private var images = ""    
			private var dimages: Bitmap? = null


    private var errors = ArrayList<String>()

    fun setSkinCancer(skincancerx: String) {
        skincancer = skincancerx
    }
    

    fun resetData() {
        skincancer = ""
    }

    fun isImageRecognitionError(): Boolean {
        errors.clear()
        instanceSkinCancer = model.getSkinCancerByPK(skincancer)
        if (instanceSkinCancer == null) {
            errors.add("skinCancer must be a valid SkinCancer id")
        }
        
             if (instanceSkinCancer!!.images != "") {
		            val x = instanceSkinCancer!!.images
		            dimages= try {
				                // convert base64 to bitmap android
				                val decodedString: ByteArray = Base64.decode(x, Base64.DEFAULT)
				                val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
				                decodedByte
				            }
				            catch (e: Exception) {
				                e.message
				                null
				            }
				        } else {
				            errors.add("This is not a type of image")
				        }


        return errors.isNotEmpty()
    }

    fun errors(): String {
        return errors.toString()
    }

     fun imageRecognition (): String {
        return model.imageRecognition(instanceSkinCancer!!, dimages!!)
     }
}
