@file:JvmMultifileClass
@file:JvmName("TypeNamesKt")

package org.ldemetrios.kotlinpoet

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.DelicateKotlinPoetApi
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.WildcardTypeName
import com.squareup.kotlinpoet.asClassName
import kotlin.reflect.KClass

public inline fun ClassName.nullable(tags: Map<KClass<*>, Any> = emptyMap()): ClassName =
    copy(nullable = true, tags = tags)

public fun ClassName.annotate(
    vararg annotations: AnnotationSpec,
    tags: Map<KClass<*>, Any> = emptyMap(),
): ClassName = copy(annotations = annotations.toList(), tags = tags)

@DelicateKotlinPoetApi(DELICATE_API)
public inline val Class<*>.name2: ClassName get() = asClassName()

public inline val KClass<*>.name: ClassName get() = asClassName()

@DelicateKotlinPoetApi(DELICATE_API)
public inline val KClass<*>.javaName: ClassName get() = java.asClassName()

/** Returns a new class name instance for the given fully-qualified class name string. */
public inline fun classNamed(fullName: String): ClassName = ClassName.bestGuess(fullName)

/** Returns a class name created from the given parts. */
public inline fun classNamed(
    packageName: String,
    simpleName: String,
    vararg simpleNames: String,
): ClassName = ClassName(packageName, simpleName, *simpleNames)

public operator fun ClassName.get(types: List<TypeName>): TypeName = invoke(*types.toTypedArray())
public operator fun ClassName.invoke(vararg types: TypeName): TypeName = if (types.isEmpty()) this else this.parameterizedBy(*types)

public operator fun TypeName.unaryPlus(): WildcardTypeName = WildcardTypeName.producerOf(this)
public operator fun TypeName.unaryMinus(): WildcardTypeName = WildcardTypeName.consumerOf(this)
