package com.ramesh.awesomeslider

class SliderDataModel {
    var imageUrl: String? = null
    var imageDrawablel: Int? = null
    var imageDescription: String

    constructor(imageUrl: String, imageDescription: String) {
        this.imageDescription = imageDescription
        this.imageUrl = imageUrl
    }

    constructor(imageDrawable: Int, imageDescription: String) {
        this.imageDescription = imageDescription
        this.imageDrawablel = imageDrawable
    }

}