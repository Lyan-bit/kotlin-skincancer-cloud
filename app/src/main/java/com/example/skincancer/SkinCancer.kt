package com.example.skincancer

import java.util.HashMap

class SkinCancer {

    init {
        SkinCancerAllInstances.add(this)
    }

    companion object {
        var SkinCancerAllInstances = ArrayList<SkinCancer>()
        fun createSkinCancer(): SkinCancer {
            return SkinCancer()
        }
        
        var SkinCancerIndex: HashMap<String, SkinCancer> = HashMap<String, SkinCancer>()
        
        fun createByPKSkinCancer(idx: String): SkinCancer {
            var result: SkinCancer? = SkinCancerIndex[idx]
            if (result != null) { return result }
                  result = SkinCancer()
                  SkinCancerIndex.put(idx,result)
                  result.id = idx
                  return result
        }
        
		fun killSkinCancer(idx: String?) {
            val rem = SkinCancerIndex[idx] ?: return
            val remd = ArrayList<SkinCancer>()
            remd.add(rem)
            SkinCancerIndex.remove(idx)
            SkinCancerAllInstances.removeAll(remd)
        }        
    }

    var id = ""  /* identity */
    var dates = "" 
    var images = "" 
    var outcome = ""  /* derived */

}
