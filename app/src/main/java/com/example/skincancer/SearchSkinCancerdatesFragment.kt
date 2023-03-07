package com.example.skincancer

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.*
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import java.lang.Exception
import java.util.ArrayList
import androidx.lifecycle.lifecycleScope
import android.util.Base64
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class SearchSkinCancerdatesFragment : Fragment(), View.OnClickListener, AdapterView.OnItemSelectedListener {
	private lateinit var root: View
	private lateinit var myContext: Context
	private lateinit var model: ModelFacade
	
	private lateinit var skinCancerBean: SkinCancerBean
	
	private lateinit var searchSkinCancerSpinner: Spinner
	private var allSkinCancerdatess: List<String> = ArrayList()
	
	private lateinit var datesTextField: EditText
	private var datesData = ""
	
	private lateinit var searchSkinCancerButton : Button
	private lateinit var cancelsearchSkinCancerButton : Button		
	
	private lateinit var idTextView: TextView
	private lateinit var datesTextView: TextView
	private lateinit var imagesImageView: ImageView
		private var imagesData = ""
		var dimages: Bitmap? = null
	private lateinit var outcomeTextView: TextView
		
    companion object {
        fun newInstance(c: Context): SearchSkinCancerdatesFragment {
            val fragment = SearchSkinCancerdatesFragment()
            val args = Bundle()
            fragment.arguments = args
            fragment.myContext = c
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		root = inflater.inflate(R.layout.searchskincancerdates_layout, container, false)
	    return root
	}
	
	override fun onResume() {
		super.onResume()
		model = ModelFacade.getInstance(myContext)
		skinCancerBean = SkinCancerBean(myContext)

		searchSkinCancerSpinner = root.findViewById(R.id.searchSkinCancerSpinner)
		
		Log.i("model",model.listSkinCancer().toString())
		Log.i("model",model.allSkinCancerIds().toString())

		allSkinCancerdatess = model.searchSkinCancer()
		val searchSkinCancerAdapter =
		ArrayAdapter(myContext, android.R.layout.simple_spinner_item, allSkinCancerdatess)
		searchSkinCancerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
		searchSkinCancerSpinner.adapter = searchSkinCancerAdapter
		searchSkinCancerSpinner.onItemSelectedListener = this

		searchSkinCancerButton = root.findViewById(R.id.searchSkinCancerOK)
		searchSkinCancerButton.setOnClickListener(this)
		cancelsearchSkinCancerButton = root.findViewById(R.id.searchSkinCancerCancel)
		cancelsearchSkinCancerButton.setOnClickListener(this)
		
		idTextView = root.findViewById(R.id.searchSkinCanceridView)
		datesTextView = root.findViewById(R.id.searchSkinCancerdatesView)
	imagesImageView = root.findViewById(R.id.searchSkinCancerimagesImageView)
		outcomeTextView = root.findViewById(R.id.searchSkinCanceroutcomeView)
		datesTextField = root.findViewById(R.id.searchSkinCancerField)

	}
	
	override fun onItemSelected(parent: AdapterView<*>, v: View?, position: Int, id: Long) {
		if (parent === searchSkinCancerSpinner) {
			datesTextField.setText(allSkinCancerdatess[position])
		}
	}

	override fun onNothingSelected(parent: AdapterView<*>?) {
		//onNothingSelected
	}

	override fun onClick(v: View?) {
	val imm = myContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
	try {
		imm.hideSoftInputFromWindow(v?.windowToken, 0)
		} catch (e: Exception) {
			 e.message
		}

	when (v?.id) {
		R.id.searchSkinCancerOK-> {
			searchSkinCancerOK()
		}
		R.id.searchSkinCancerCancel-> {
			searchSkinCancerCancel()
		}
	  }
    }

	private fun searchSkinCancerOK() {
		datesData = datesTextField.text.toString()
		skinCancerBean.setDates(datesData)
		
		if (skinCancerBean.isSearchSkinCancerError(allSkinCancerdatess)) {
			Log.w(javaClass.name, skinCancerBean.errors())
			Toast.makeText(myContext, "Errors: " + skinCancerBean.errors(), Toast.LENGTH_LONG).show()
		} else {
				val selectedItem = model.searchSkinCancer(datesData)
				idTextView.text = selectedItem[0].id.toString()
				datesTextView.text = selectedItem[0].dates.toString()
      dimages = try {
				// convert base64 to bitmap android
				val decodedString: ByteArray = Base64.decode(selectedItem[0].images, Base64.DEFAULT)
				val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
					decodedByte
				}
				catch (e: Exception) {
					   e.message
					   null
				}
				viewLifecycleOwner.lifecycleScope.launchWhenStarted {
					imagesImageView.setImageBitmap(dimages)
				}
				outcomeTextView.text = selectedItem[0].outcome.toString()
				Toast.makeText(myContext, "search SkinCancer done!", Toast.LENGTH_LONG).show()
				
		}
	}

	private fun searchSkinCancerCancel() {
		skinCancerBean.resetData()
		idTextView.text = ""
		datesTextView.text = ""
		imagesImageView.setImageResource(0)
		outcomeTextView.text = ""
    }	 
}
