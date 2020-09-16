package com.gvfiqst.tontestapp.domain.mappings


interface Mapper<in In, out Out> {

    fun map(model: In): Out

}
