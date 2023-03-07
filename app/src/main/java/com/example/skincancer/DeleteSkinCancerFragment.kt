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

class DeleteSkinCancerFragment : Fragment(), View.OnClickListener, AdapterView.OnItemSelectedListener {
	private lateinit var root: View
	private lateinit var myContext: Context
	private lateinit var model: ModelFacade
	
	private lateinit var skinCancerBean: SkinCancerBean
	
	private lateinit var deleteSkinCancerSpinner: Spinner
	private var allSkinCancerids: List<String> = ArrayList()
	private lateinit var idTextField: EditText
	private var idData = ""
	private lateinit var deleteSkinCancerButton : Button
	private lateinit var cancelSkinCancerButton : Button	
	
    companion object {
        fun newInstance(c: Context): DeleteSkinCancerFragment {
            val fragment = DeleteSkinCancerFragment()
            val args = Bundle()
            fragment.arguments = args
            fragment.myContext = c
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		root = inflater.inflate(R.layout.deleteskincancer_layout, container, false)
	    return root
	}
	
	override fun onResume() {
		super.onResume()
		model = ModelFacade.getInstance(myContext)
		skinCancerBean = SkinCancerBean(myContext)

		idTextField = root.findViewById(R.id.crudSkinCanceridField)
		deleteSkinCancerSpinner = root.findViewById(R.id.crudSkinCancerSpinner)
		Log.i("model",model.listSkinCancer().toString())
		Log.i("model",model.allSkinCancerIds().toString())

		allSkinCancerids = model.allSkinCancerIds()
		val deleteSkinCancerAdapter =
		ArrayAdapter(myContext, android.R.layout.simple_spinner_item, allSkinCancerids)
		deleteSkinCancerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
		deleteSkinCancerSpinner.adapter = deleteSkinCancerAdapter
		deleteSkinCancerSpinner.onItemSelectedListener = this

		deleteSkinCancerButton = root.findViewById(R.id.crudSkinCancerOK)
		deleteSkinCancerButton.setOnClickListener(this)
		cancelSkinCancerButton = root.findViewById(R.id.crudSkinCancerCancel)
		cancelSkinCancerButton.setOnClickListener(this)
	}
	
	override fun onItemSelected(parent: AdapterView<*>, v: View?, position: Int, id: Long) {
		if (parent === deleteSkinCancerSpinner) {
			idTextField.setText(allSkinCancerids[position])
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
		R.id.crudSkinCancerOK-> {
			crudSkinCancerOK()
		}
		R.id.crudSkinCancerCancel-> {
			crudSkinCancerCancel()
		}
	  }
    }

	private fun crudSkinCancerOK() {
		idData = idTextField.text.toString()
		skinCancerBean.setId(idData)
		if (skinCancerBean.isDeleteSkinCancerError(allSkinCancerids)) {
			Log.w(javaClass.name, skinCancerBean.errors())
			Toast.makeText(myContext, "Errors: " + skinCancerBean.errors(), Toast.LENGTH_LONG).show()
		} else {
		skinCancerBean.deleteSkinCancer()
		Toast.makeText(myContext, "skinCancer is deleted!", Toast.LENGTH_LONG).show()
		}
	}

	private fun crudSkinCancerCancel() {
		skinCancerBean.resetData()
		idTextField.setText("")
	}
		 
}
