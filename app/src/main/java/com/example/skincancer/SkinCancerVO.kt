package com.example.skincancer

import java.util.ArrayList

class SkinCancerVO  {

     var id: String = ""
     var dates: String = ""
     var images: String = ""
     var outcome: String = ""

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
    
}
