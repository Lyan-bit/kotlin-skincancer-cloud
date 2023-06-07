package com.example.skincancer

import org.json.JSONObject
import java.lang.Exception
import org.json.JSONArray
import kotlin.collections.ArrayList

class SkinCancerDAO {

    companion object {

        fun getURL(command: String?, pars: ArrayList<String>, values: ArrayList<String>): String {
            var res = "base url for the data source"
            if (command != null) {
                res += command
            }
            if (pars.isEmpty()) {
                return res
            }
            res = "$res?"
            for (item in pars.indices) {
                val par = pars[item]
                val vals = values[item]
                res = "$res$par=$vals"
                if (item < pars.size - 1) {
                    res = "$res&"
                }
            }
            return res
        }

        fun isCached(id: String?): Boolean {
            SkinCancer.SkinCancerIndex[id] ?: return false
            return true
        }

        fun getCachedInstance(id: String): SkinCancer? {
            return SkinCancer.SkinCancerIndex[id]
        }

      fun parseCSV(line: String?): SkinCancer? {
          if (line == null) {
              return null
          }
          val line1vals: ArrayList<String> = Ocl.tokeniseCSV(line)
          var skinCancerx: SkinCancer? = SkinCancer.SkinCancerIndex[line1vals[0]]
          if (skinCancerx == null) {
              skinCancerx = SkinCancer.createByPKSkinCancer(line1vals[0])
          }
          skinCancerx.id = line1vals[0].toString()
          skinCancerx.dates = line1vals[1].toString()
          skinCancerx.images = line1vals[2].toString()
          skinCancerx.outcome = line1vals[3].toString()
          return skinCancerx
      }


        fun parseJSON(obj: JSONObject?): SkinCancer? {
            return if (obj == null) {
                null
            } else try {
                val id = obj.getString("id")
                var skinCancerx: SkinCancer? = SkinCancer.SkinCancerIndex[id]
                if (skinCancerx == null) {
                    skinCancerx = SkinCancer.createByPKSkinCancer(id)
                }
                skinCancerx.id = obj.getString("id")
                skinCancerx.dates = obj.getString("dates")
                skinCancerx.images = obj.getString("images")
                skinCancerx.outcome = obj.getString("outcome")
                skinCancerx
            } catch (e: Exception) {
                null
            }
        }

      fun makeFromCSV(lines: String?): ArrayList<SkinCancer> {
          val result: ArrayList<SkinCancer> = ArrayList<SkinCancer>()
          if (lines == null) {
              return result
          }
          val rows: ArrayList<String> = Ocl.parseCSVtable(lines)
          for (item in rows.indices) {
              val row = rows[item]
              if (row == null || row.trim { it <= ' ' }.isEmpty()) {
                  //e
              } else {
                  val x: SkinCancer? = parseCSV(row)
                  if (x != null) {
                      result.add(x)
                  }
              }
          }
          return result
      }


        fun parseJSONArray(jarray: JSONArray?): ArrayList<SkinCancer>? {
            if (jarray == null) {
                return null
            }
            val res: ArrayList<SkinCancer> = ArrayList<SkinCancer>()
            val len = jarray.length()
            for (i in 0 until len) {
                try {
                    val x = jarray.getJSONObject(i)
                    if (x != null) {
                        val y: SkinCancer? = parseJSON(x)
                        if (y != null) {
                            res.add(y)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            return res
        }


        fun writeJSON(x: SkinCancer): JSONObject? {
            val result = JSONObject()
            try {
                result.put("id", x.id)
                result.put("dates", x.dates)
                result.put("images", x.images)
                result.put("outcome", x.outcome)
            } catch (e: Exception) {
                return null
            }
            return result
        }


        fun parseRaw(obj: Any?): SkinCancer? {
             if (obj == null) {
                 return null
            }
            try {
                val map = obj as HashMap<String, Object>
                val id: String = map["id"].toString()
                var skinCancerx: SkinCancer? = SkinCancer.SkinCancerIndex[id]
                if (skinCancerx == null) {
                    skinCancerx = SkinCancer.createByPKSkinCancer(id)
                }
                skinCancerx.id = map["id"].toString()
                skinCancerx.dates = map["dates"].toString()
                skinCancerx.images = map["images"].toString()
                skinCancerx.outcome = map["outcome"].toString()
                return skinCancerx
            } catch (e: Exception) {
                return null
            }
        }

        fun writeJSONArray(es: ArrayList<SkinCancer>): JSONArray {
            val result = JSONArray()
            for (i in 0 until es.size) {
                val ex: SkinCancer = es[i]
                val jx = writeJSON(ex)
                if (jx == null) {
                    //null
                } else {
                    try {
                        result.put(jx)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
            return result
        }
    }
}
