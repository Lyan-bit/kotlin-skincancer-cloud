package com.example.skincancer

import java.util.ArrayList

class SkinCancerVO  {

    private var id: String = ""
    private var dates: String = ""
    private var images: String = ""
    private var outcome: String = ""

    constructor() {
    	//constructor
    }

    constructor(idx: String, 
        datesx: String, 
        imagesx: String, 
        outcomex: String
        ) {
        this.id = idx
        this.dates = datesx
        this.images = imagesx
        this.outcome = outcomex
    }

    constructor (x: SkinCancer) {
        id = x.id
        dates = x.dates
        images = x.images
        outcome = x.outcome
    }

    override fun toString(): String {
        return "id = $id,dates = $dates,images = $images,outcome = $outcome"
    }

    fun toStringList(list: List<SkinCancerVO>): List<String> {
        val res: MutableList<String> = ArrayList()
        for (i in list.indices) {
            res.add(list[i].toString())
        }
        return res
    }
    
    fun getId(): String {
        return id
    }
    
    fun getDates(): String {
        return dates
    }
    
    fun getImages(): String {
        return images
    }
    
    fun getOutcome(): String {
        return outcome
    }
    

    fun setId(x: String) {
    	id = x
    }
    
    fun setDates(x: String) {
    	dates = x
    }
    
    fun setImages(x: String) {
    	images = x
    }
    
    fun setOutcome(x: String) {
    	outcome = x
    }
    
}
