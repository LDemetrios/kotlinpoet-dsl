package com.hendraanggrian.kotlinpoet.dsl

import com.hendraanggrian.kotlinpoet.KotlinpoetDslMarker
import com.hendraanggrian.kotlinpoet.TypeAliasSpecBuilder
import com.hendraanggrian.kotlinpoet.buildTypeAlias
import com.squareup.kotlinpoet.TypeAliasSpec
import com.squareup.kotlinpoet.TypeName
import java.lang.reflect.Type
import kotlin.reflect.KClass

private interface TypeAliasSpecAddable {

    /** Add type alias to this container. */
    fun add(spec: TypeAliasSpec)
}

/** An [TypeAliasSpecContainer] is responsible for managing a set of type alias instances. */
abstract class TypeAliasSpecContainer internal constructor() : TypeAliasSpecAddable {

    /** Add type alias from [name] and [type], returning the type alias added. */
    fun add(name: String, type: TypeName): TypeAliasSpec = buildTypeAlias(name, type).also { add(it) }

    /** Add type alias from [name] and [type] with custom initialization [builderAction], returning the type alias added. */
    inline fun add(name: String, type: TypeName, builderAction: TypeAliasSpecBuilder.() -> Unit): TypeAliasSpec =
        buildTypeAlias(name, type, builderAction).also { add(it) }

    /** Add type alias from [name] and [type], returning the type alias added. */
    fun add(name: String, type: Type): TypeAliasSpec = buildTypeAlias(name, type).also { add(it) }

    /** Add type alias from [name] and [type] with custom initialization [builderAction], returning the type alias added. */
    inline fun add(name: String, type: Type, builderAction: TypeAliasSpecBuilder.() -> Unit): TypeAliasSpec =
        buildTypeAlias(name, type, builderAction).also { add(it) }

    /** Add type alias from [name] and [type], returning the type alias added. */
    fun add(name: String, type: KClass<*>): TypeAliasSpec = buildTypeAlias(name, type).also { add(it) }

    /** Add type alias from [name] and [type] with custom initialization [builderAction], returning the type alias added. */
    inline fun add(name: String, type: KClass<*>, builderAction: TypeAliasSpecBuilder.() -> Unit): TypeAliasSpec =
        buildTypeAlias(name, type, builderAction).also { add(it) }

    /** Add type alias from [name] and reified [T], returning the type alias added. */
    inline fun <reified T> add(name: String): TypeAliasSpec = buildTypeAlias<T>(name).also { add(it) }

    /** Add type alias from [name] and reified [T] with custom initialization [builderAction], returning the type alias added. */
    inline fun <reified T> add(name: String, builderAction: TypeAliasSpecBuilder.() -> Unit): TypeAliasSpec =
        buildTypeAlias<T>(name, builderAction).also { add(it) }

    /** Convenient method to add type alias with operator function. */
    operator fun plusAssign(spec: TypeAliasSpec) {
        add(spec)
    }

    /** Configure this container with DSL. */
    inline operator fun invoke(configuration: TypeAliasSpecContainerScope.() -> Unit): Unit =
        TypeAliasSpecContainerScope(this).configuration()
}

/** Receiver for the `typeAliases` block providing an extended set of operators for the configuration. */
@KotlinpoetDslMarker
class TypeAliasSpecContainerScope @PublishedApi internal constructor(container: TypeAliasSpecContainer) :
    TypeAliasSpecContainer(), TypeAliasSpecAddable by container {

    /** Convenient method to add type alias with receiver type. */
    inline operator fun String.invoke(type: TypeName, builderAction: TypeAliasSpecBuilder.() -> Unit): TypeAliasSpec =
        add(this, type, builderAction)

    /** Convenient method to add type alias with receiver type. */
    inline operator fun String.invoke(type: Type, builderAction: TypeAliasSpecBuilder.() -> Unit): TypeAliasSpec =
        add(this, type, builderAction)

    /** Convenient method to add type alias with receiver type. */
    inline operator fun String.invoke(type: KClass<*>, builderAction: TypeAliasSpecBuilder.() -> Unit): TypeAliasSpec =
        add(this, type, builderAction)

    /** Convenient method to add type alias with receiver type. */
    inline operator fun <reified T> String.invoke(builderAction: TypeAliasSpecBuilder.() -> Unit): TypeAliasSpec =
        add(this, T::class, builderAction)
}
