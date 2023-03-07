package com.example.skincancer

import android.content.Context
import java.lang.Exception
import java.util.regex.Pattern

class SkinCancerBean(c: Context) {

    private var model: ModelFacade = ModelFacade.getInstance(c)

    private var id = ""
    private var dates = ""
    private var images = ""
    private var outcome = ""

    private var errors = ArrayList<String>()

    fun setId(idx: String) {
	 id = idx
    }
    
    fun setDates(datesx: String) {
	 dates = datesx
    }
    
    fun setImages(imagesx: String) {
	 images = imagesx
    }
    
    fun setOutcome(outcomex: String) {
	 outcome = outcomex
    }
    


    fun resetData() {
	  id = ""
	  dates = ""
	  images = ""
	  outcome = ""
    }
    
    fun isCreateSkinCancerError(): Boolean {
	        
	        errors.clear()
	        
          if (id != "") {
	//validate
}
	         else {
	               errors.add("id cannot be empty")
	         }
	            if (validateDate(dates)) {
		//validateDate
	}
	         else {
	               errors.add("dates should written as \"DD-MM-YYYY\"")
	         }
          if (images != "") {
	//validate
}
	         else {
	               errors.add("images cannot be empty")
	         }
	
	        return errors.size > 0
	    }
	    
	    fun createSkinCancer() {
	        model.createSkinCancer(SkinCancerVO(id, dates, images, outcome))
	        resetData()
	    }
	   
     fun editSkinCancer() {
		     model.editSkinCancer(SkinCancerVO(id, dates, images, outcome))
		     resetData()
		 }
		       
		 fun isEditSkinCancerError(allSkinCancerids: List<String>): Boolean {
       
       errors.clear()
			
			if (!allSkinCancerids.contains(id)) {
				errors.add("The id is not exist")
		    }
          if (id != "") {
	//validate
}
	         else {
	               errors.add("id cannot be empty")
	         }
            if (validateDate(dates)) {
	//validateDate
}
	                else {
	                    errors.add("dates should written as \"DD-MM-YYYY\"")
	                }
          if (images != "") {
	//validate
}
	         else {
	               errors.add("images cannot be empty")
	         }

       return errors.size > 0
   }
       
   fun deleteSkinCancer() {
       model.deleteSkinCancer(id)
       resetData()
   }
   
   fun isDeleteSkinCancerError(allSkinCancerids: List<String>): Boolean {
        errors.clear()
			 if (!allSkinCancerids.contains(id)) {
			    errors.add("The id is not exist")
        }
        return errors.size > 0
		}    

   	fun isSearchSkinCancerError(allSkinCancerdatess: List<String>): Boolean {
        errors.clear()
        if (!allSkinCancerdatess.contains(dates)) {
            errors.add("The dates is not exist")
        }
        return errors.size > 0
    }
    
		fun isSearchSkinCancerIdError(allSkinCancerIds: List<String>): Boolean {
    	   errors.clear()
   	       if (!allSkinCancerIds.contains(id)) {
    	       errors.add("The id is not exist")
    	   }
           return errors.size > 0
    }

    fun errors(): String {
        return errors.toString()
    }


private fun validateDate(date: String): Boolean {
       val regex = "^(1[0-9]|0[1-9]|3[0-1]|2[1-9])-(0[1-9]|1[0-2])-[0-9]{4}$"
       val matcher = Pattern.compile(regex).matcher(date)
       return if (matcher.matches()) {
           matcher.reset()
           if (matcher.find()) {
               val dateDetails = date.split("-")
               val day: String = dateDetails[1]
               val month: String = dateDetails[0]
               val year: String = dateDetails[2]
               if (validateMonthWithMaxDate(day, month)) {
                   false
               } else if (isFebruaryMonth(month)) {
                   if (isLeapYear(year)) {
                       leapYearWith29Date(day)
                   } else {
                       notLeapYearFebruary(day)
                   }
               } else {
                   true
               }
           } else {
               false
           }
       } else {
           false
       }
   }
	
   private fun validateMonthWithMaxDate(day: String, month: String): Boolean = day == "31" && (month == "11" || month == "04" || month == "06" || month == "09")
   private fun isFebruaryMonth(month: String): Boolean = month == "02"
   private fun isLeapYear(year: String): Boolean = year.toInt() % 4 == 0
   private fun leapYearWith29Date(day: String): Boolean = !(day == "30" || day == "31")
   private fun notLeapYearFebruary(day: String): Boolean = !(day == "29" || day == "30" || day == "31")

}

