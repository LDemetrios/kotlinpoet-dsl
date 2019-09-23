package com.hendraanggrian.kotlinpoet

import com.hendraanggrian.kotlinpoet.dsl.KdocContainer
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeAliasSpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeVariableName
import java.lang.reflect.Type
import kotlin.reflect.KClass

/** Builds a new [TypeAliasSpec] from [name] and [type]. */
fun buildTypeAlias(name: String, type: TypeName): TypeAliasSpec =
    TypeAliasSpec.builder(name, type).build()

/**
 * Builds a new [TypeAliasSpec] from [name] and [type],
 * by populating newly created [TypeAliasSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildTypeAlias(
    name: String,
    type: TypeName,
    builderAction: TypeAliasSpecBuilder.() -> Unit
): TypeAliasSpec = TypeAliasSpecBuilder(TypeAliasSpec.builder(name, type)).apply(builderAction).build()

/** Builds a new [TypeAliasSpec] from [name] and [type]. */
fun buildTypeAlias(name: String, type: Type): TypeAliasSpec =
    TypeAliasSpec.builder(name, type).build()

/** Builds a new [TypeAliasSpec] from [name] and [type]. */
fun buildTypeAlias(name: String, type: KClass<*>): TypeAliasSpec =
    buildTypeAlias(name, type.java)

/** Builds a new [TypeAliasSpec] from [name] and [T]. */
inline fun <reified T> buildTypeAlias(name: String): TypeAliasSpec =
    buildTypeAlias(name, T::class.java)

/**
 * Builds a new [TypeAliasSpec] from [name] and [type],
 * by populating newly created [TypeAliasSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildTypeAlias(
    name: String,
    type: Type,
    builderAction: TypeAliasSpecBuilder.() -> Unit
): TypeAliasSpec = TypeAliasSpecBuilder(TypeAliasSpec.builder(name, type)).apply(builderAction).build()

/**
 * Builds a new [TypeAliasSpec] from [name] and [type],
 * by populating newly created [TypeAliasSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildTypeAlias(
    name: String,
    type: KClass<*>,
    builderAction: TypeAliasSpecBuilder.() -> Unit
): TypeAliasSpec = buildTypeAlias(name, type.java, builderAction)

/**
 * Builds a new [TypeAliasSpec] from [name] and [T],
 * by populating newly created [TypeAliasSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun <reified T> buildTypeAlias(
    name: String,
    builderAction: TypeAliasSpecBuilder.() -> Unit
): TypeAliasSpec = buildTypeAlias(name, T::class, builderAction)

/** Wrapper of [TypeAliasSpec.Builder], providing DSL support as a replacement to Java builder. */
@KotlinpoetDslMarker
class TypeAliasSpecBuilder @PublishedApi internal constructor(private val nativeBuilder: TypeAliasSpec.Builder) {

    /** Modifiers of this builder. */
    val modifiers: MutableSet<KModifier>
        get() = nativeBuilder.modifiers

    /** Type variables of this builder. */
    val typeVariables: MutableSet<TypeVariableName>
        get() = nativeBuilder.typeVariables

    /** Add field modifiers. */
    fun addModifiers(vararg modifiers: KModifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    /** Add type variables. */
    fun addTypeVariables(typeVariables: Iterable<TypeVariableName>) {
        nativeBuilder.addTypeVariables(typeVariables)
    }

    /** Add type variables. */
    fun addTypeVariable(typeVariable: TypeVariableName) {
        nativeBuilder.addTypeVariable(typeVariable)
    }

    /** Collection of kdoc, may be configured with Kotlin DSL. */
    val kdoc: KdocContainer = object : KdocContainer() {
        override fun append(format: String, vararg args: Any) {
            nativeBuilder.addKdoc(format, *args)
        }

        override fun append(code: CodeBlock) {
            nativeBuilder.addKdoc(code)
        }
    }

    /** Returns native spec. */
    fun build(): TypeAliasSpec =
        nativeBuilder.build()
}
