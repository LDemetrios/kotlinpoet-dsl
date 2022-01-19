@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.kotlinpoet.collections

import com.hendraanggrian.kotlinpoet.PropertySpecBuilder
import com.hendraanggrian.kotlinpoet.SpecMarker
import com.hendraanggrian.kotlinpoet.buildPropertySpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import java.lang.reflect.Type
import kotlin.reflect.KClass

/** A [PropertySpecCollection] is responsible for managing a set of property instances. */
open class PropertySpecCollection internal constructor(actualList: MutableList<PropertySpec>) :
    MutableList<PropertySpec> by actualList {

    /** Add property from [TypeName]. */
    fun add(name: String, type: TypeName, vararg modifiers: KModifier): Boolean =
        add(PropertySpec.builder(name, type, *modifiers).build())

    /** Add property from [TypeName] with custom initialization [configuration]. */
    fun add(
        name: String,
        type: TypeName,
        vararg modifiers: KModifier,
        configuration: PropertySpecBuilder.() -> Unit
    ): Boolean = add(buildPropertySpec(name, type, *modifiers, configuration = configuration))

    /** Add property from [Type]. */
    fun add(name: String, type: Type, vararg modifiers: KModifier): Boolean =
        add(PropertySpec.builder(name, type, *modifiers).build())

    /** Add property from [Type] with custom initialization [configuration]. */
    fun add(
        name: String,
        type: Type,
        vararg modifiers: KModifier,
        configuration: PropertySpecBuilder.() -> Unit
    ): Boolean = add(buildPropertySpec(name, type, *modifiers, configuration = configuration))

    /** Add property from [KClass]. */
    fun add(name: String, type: KClass<*>, vararg modifiers: KModifier): Boolean =
        add(PropertySpec.builder(name, type, *modifiers).build())

    /** Add property from [KClass] with custom initialization [configuration]. */
    fun add(
        name: String,
        type: KClass<*>,
        vararg modifiers: KModifier,
        configuration: PropertySpecBuilder.() -> Unit
    ): Boolean = add(buildPropertySpec(name, type, *modifiers, configuration = configuration))

    /** Add property from [T]. */
    inline fun <reified T> add(name: String, vararg modifiers: KModifier): Boolean =
        add(PropertySpec.builder(name, T::class, *modifiers).build())

    /** Add property from [T] with custom initialization [configuration]. */
    inline fun <reified T> add(
        name: String,
        vararg modifiers: KModifier,
        noinline configuration: PropertySpecBuilder.() -> Unit
    ): Boolean = add(buildPropertySpec<T>(name, *modifiers, configuration = configuration))

    /** Convenient method to add property with operator function. */
    inline operator fun set(name: String, type: TypeName) {
        add(name, type)
    }

    /** Convenient method to add property with operator function. */
    inline operator fun set(name: String, type: Type) {
        add(name, type)
    }

    /** Convenient method to add property with operator function. */
    inline operator fun set(name: String, type: KClass<*>) {
        add(name, type)
    }
}

/** Receiver for the `properties` block providing an extended set of operators for the configuration. */
@SpecMarker
class PropertySpecCollectionScope internal constructor(actualList: MutableList<PropertySpec>) :
    PropertySpecCollection(actualList) {

    /** @see PropertySpecCollection.add */
    operator fun String.invoke(
        type: TypeName,
        vararg modifiers: KModifier,
        configuration: PropertySpecBuilder.() -> Unit
    ): Boolean = add(this, type, *modifiers, configuration = configuration)

    /** @see PropertySpecCollection.add */
    operator fun String.invoke(
        type: Type,
        vararg modifiers: KModifier,
        configuration: PropertySpecBuilder.() -> Unit
    ): Boolean = add(this, type, *modifiers, configuration = configuration)

    /** @see PropertySpecCollection.add */
    operator fun String.invoke(
        type: KClass<*>,
        vararg modifiers: KModifier,
        configuration: PropertySpecBuilder.() -> Unit
    ): Boolean = add(this, type, *modifiers, configuration = configuration)

    /** @see PropertySpecCollection.add */
    inline operator fun <reified T> String.invoke(
        vararg modifiers: KModifier,
        noinline configuration: PropertySpecBuilder.() -> Unit
    ): Boolean = add<T>(this, *modifiers, configuration = configuration)
}
