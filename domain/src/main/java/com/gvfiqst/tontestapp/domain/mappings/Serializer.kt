package com.gvfiqst.tontestapp.domain.mappings


interface Serializer<In, Out> {

    fun serialize(input: In): Out

    fun deserialize(input: Out): In

}
