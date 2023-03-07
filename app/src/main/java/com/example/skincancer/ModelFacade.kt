package com.example.skincancer

import android.content.Context
import java.util.ArrayList
import android.graphics.Bitmap
import android.content.res.AssetManager
import org.tensorflow.lite.Interpreter


class ModelFacade private constructor(context: Context) {

    private var cdb: FirebaseDB = FirebaseDB.getInstance()
    private val assetManager: AssetManager = context.assets
    private var fileSystem: FileAccessor
    private var imageClassifier: ImageClassifier

    private var currentSkinCancer: SkinCancerVO? = null
    private var currentSkinCancers: ArrayList<SkinCancerVO> = ArrayList()

    init {
    	//init
        fileSystem = FileAccessor(context)
        imageClassifier = ImageClassifier(context)
	}

    companion object {
        private var instance: ModelFacade? = null
        fun getInstance(context: Context): ModelFacade {
            return instance ?: ModelFacade(context)
        }
    }
    
	/* This metatype code requires OclType.java, OclAttribute.java, OclOperation.java */
	fun initialiseOclTypes() {
			val skinCancerOclType: OclType = OclType.createByPKOclType("SkinCancer")
		skinCancerOclType.setMetatype(SkinCancer::class.java)
		    }
    
    fun createSkinCancer(x: SkinCancerVO) { 
			  editSkinCancer(x)
	}
				    
    fun editSkinCancer(x: SkinCancerVO) {
		var obj = getSkinCancerByPK(x.getId())
		if (obj == null) {
			obj = SkinCancer.createByPKSkinCancer(x.getId())
		}
			
		  obj.id = x.getId()
		  obj.dates = x.getDates()
		  obj.images = x.getImages()
		  obj.outcome = x.getOutcome()
		cdb.persistSkinCancer(obj)
		currentSkinCancer = x
	}
		
	fun deleteSkinCancer(id: String) {
			  val obj = getSkinCancerByPK(id)
			  if (obj != null) {
			      cdb.deleteSkinCancer(obj)
			          SkinCancer.killSkinCancer(id)
			      }
			  currentSkinCancer = null	
		}
				    
    fun setSelectedSkinCancer(x: SkinCancerVO) {
			  currentSkinCancer = x
	}
    fun searchSkinCancer(dates: String) : ArrayList<SkinCancer> {
			var itemsList = ArrayList<SkinCancer>()
			for (x in currentSkinCancers.indices) {
				if ( currentSkinCancers[x].getDates() == dates) {
					val vo: SkinCancerVO = currentSkinCancers[x]
				    val itemx = SkinCancer.createByPKSkinCancer(vo.getId())
	            itemx.id = vo.getId()
            itemx.dates = vo.getDates()
            itemx.images = vo.getImages()
            itemx.outcome = vo.getOutcome()
					itemsList.add(itemx)
				}
			}
			return itemsList
		}
		
	
	fun searchSkinCancer(): ArrayList<String> {
		val res: ArrayList<String> = ArrayList()
		for (x in currentSkinCancers.indices) {
			res.add(currentSkinCancers[x].getDates().toString())
		}
		return res
	}
		
    fun imageRecognition(skinCancer: SkinCancer ,images: Bitmap): String {
			val result = imageClassifier.recognizeImage(images)
	        skinCancer.outcome = result[0].title  +": " + result[0].confidence
		    persistSkinCancer(skinCancer)
	    	return result[0].title  +": " + result[0].confidence
		}
			     



	    	fun listSkinCancer(): ArrayList<SkinCancerVO> {
		  val skinCancers: ArrayList<SkinCancer> = SkinCancer.SkinCancerAllInstances
		  currentSkinCancers.clear()
		  for (i in skinCancers.indices) {
		       currentSkinCancers.add(SkinCancerVO(skinCancers[i]))
		  }
			      
		 return currentSkinCancers
	}
	
	fun listAllSkinCancer(): ArrayList<SkinCancer> {
		  val skinCancers: ArrayList<SkinCancer> = SkinCancer.SkinCancerAllInstances    
		  return skinCancers
	}
	

			    
    fun stringListSkinCancer(): ArrayList<String> {
        val res: ArrayList<String> = ArrayList()
        for (x in currentSkinCancers.indices) {
            res.add(currentSkinCancers[x].toString())
        }
        return res
    }

    fun getSkinCancerByPK(value: String): SkinCancer? {
        return SkinCancer.SkinCancerIndex[value]
    }
    
    fun retrieveSkinCancer(value: String): SkinCancer? {
            return getSkinCancerByPK(value)
    }

    fun allSkinCancerIds(): ArrayList<String> {
        val res: ArrayList<String> = ArrayList()
            for (x in currentSkinCancers.indices) {
                res.add(currentSkinCancers[x].getId())
            }
        return res
    }
    
    fun setSelectedSkinCancer(i: Int) {
        if (i < currentSkinCancers.size) {
            currentSkinCancer = currentSkinCancers[i]
        }
    }

    fun getSelectedSkinCancer(): SkinCancerVO? {
        return currentSkinCancer
    }

    fun persistSkinCancer(x: SkinCancer) {
        val vo = SkinCancerVO(x)
        cdb.persistSkinCancer(x)
        currentSkinCancer = vo
    }

		
}
