package com.extensions.bundles

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class BundlifyDelegate<T: Any>(val bundlify: Bundlify): ReadWriteProperty<Any, T> {

    override fun getValue(thisRef: Any, property: KProperty<*>) =
            bundlify.get(thisRef::class, property.name) as T

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) =
            bundlify.put(property.name, value)
}