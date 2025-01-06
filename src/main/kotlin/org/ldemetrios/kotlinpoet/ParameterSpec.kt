@file:OptIn(ExperimentalContracts::class)

package org.ldemetrios.kotlinpoet

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import java.lang.AssertionError
import java.lang.reflect.Type
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KClass

/** Creates new [ParameterSpec] using parameters. */
public inline fun parameterSpecOf(
    name: String,
    type: TypeName,
    vararg modifiers: KModifier,
): ParameterSpec =
    ParameterSpec
        .builder(name, type, *modifiers)
        .build()

/**
 * Builds new [ParameterSpec] by populating newly created [ParameterSpecBuilder] using provided
 * [configuration].
 */
public inline fun buildParameterSpec(
    name: String,
    type: TypeName,
    vararg modifiers: KModifier,
    owner: TypeSpecBuilder? = null,
    configuration: ParameterSpecBuilder.() -> Unit,
): ParameterSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return ParameterSpecBuilder(name, type, ParameterSpec.builder(name, type, *modifiers), owner)
        .apply(configuration)
        .build()
}

/**
 * Inserts new [ParameterSpec] by populating newly created [ParameterSpecBuilder] using provided
 * [configuration].
 */
public inline fun ParameterSpecHandler.add(
    name: String,
    type: TypeName,
    vararg modifiers: KModifier,
    configuration: ParameterSpecBuilder.() -> Unit,
): ParameterSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return ParameterSpecBuilder(name, type, ParameterSpec.builder(name, type, *modifiers), this.owner)
        .apply(configuration)
        .build()
        .also(::add)
}

/**
 * Inserts new [ParameterSpec] by populating newly created [ParameterSpecBuilder] using provided
 * [configuration].
 */
public inline fun ParameterSpecHandler.add(
    name: String,
    type: Type,
    vararg modifiers: KModifier,
    configuration: ParameterSpecBuilder.() -> Unit,
): ParameterSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return ParameterSpecBuilder(name, type.asTypeName(), ParameterSpec.builder(name, type, *modifiers), this.owner)
        .apply(configuration)
        .build()
        .also(::add)
}

/**
 * Inserts new [ParameterSpec] by populating newly created [ParameterSpecBuilder] using provided
 * [configuration].
 */
public inline fun ParameterSpecHandler.add(
    name: String,
    type: KClass<*>,
    vararg modifiers: KModifier,
    configuration: ParameterSpecBuilder.() -> Unit,
): ParameterSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return ParameterSpecBuilder(name, type.asTypeName(),ParameterSpec.builder(name, type, *modifiers), this.owner)
        .apply(configuration)
        .build()
        .also(::add)
}

/**
 * Property delegate for inserting new [ParameterSpec] by populating newly created
 * [ParameterSpecBuilder] using provided [configuration].
 */
public fun ParameterSpecHandler.adding(
    type: TypeName,
    vararg modifiers: KModifier,
    configuration: ParameterSpecBuilder.() -> Unit,
): SpecDelegateProvider<ParameterSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        ParameterSpecBuilder(it, type, ParameterSpec.builder(it, type, *modifiers), this.owner)
            .apply(configuration)
            .build()
            .also(::add)
    }
}

/**
 * Property delegate for inserting new [ParameterSpec] by populating newly created
 * [ParameterSpecBuilder] using provided [configuration].
 */
public fun ParameterSpecHandler.adding(
    type: Type,
    vararg modifiers: KModifier,
    configuration: ParameterSpecBuilder.() -> Unit,
): SpecDelegateProvider<ParameterSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        ParameterSpecBuilder(it, type.asTypeName(), ParameterSpec.builder(it, type, *modifiers), this.owner)
            .apply(configuration)
            .build()
            .also(::add)
    }
}

/**
 * Property delegate for inserting new [ParameterSpec] by populating newly created
 * [ParameterSpecBuilder] using provided [configuration].
 */
public fun ParameterSpecHandler.adding(
    type: KClass<*>,
    vararg modifiers: KModifier,
    configuration: ParameterSpecBuilder.() -> Unit,
): SpecDelegateProvider<ParameterSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        ParameterSpecBuilder(it, type.asTypeName(), ParameterSpec.builder(it, type, *modifiers), this.owner)
            .apply(configuration)
            .build()
            .also(::add)
    }
}

/** Convenient method to insert [ParameterSpec] using reified type. */
public inline fun <reified T> ParameterSpecHandler.add(
    name: String,
    vararg modifiers: KModifier,
): ParameterSpec =
    ParameterSpec
        .builder(name, T::class, *modifiers)
        .build()
        .also(::add)

/** Responsible for managing a set of [ParameterSpec] instances. */
public interface ParameterSpecHandler {
    public val owner: TypeSpecBuilder?

    public fun add(parameter: ParameterSpec)

    public fun add(name: String, type: TypeName, vararg modifiers: KModifier): ParameterSpec =
        parameterSpecOf(name, type, *modifiers).also(::add)

    public fun add(name: String, type: Type, vararg modifiers: KModifier): ParameterSpec =
        parameterSpecOf(name, type.name, *modifiers).also(::add)

    public fun add(name: String, type: KClass<*>, vararg modifiers: KModifier): ParameterSpec =
        parameterSpecOf(name, type.name, *modifiers).also(::add)

    public fun adding(
        type: TypeName,
        vararg modifiers: KModifier,
    ): SpecDelegateProvider<ParameterSpec> =
        SpecDelegateProvider { parameterSpecOf(it, type, *modifiers).also(::add) }

    public fun adding(
        type: Type,
        vararg modifiers: KModifier,
    ): SpecDelegateProvider<ParameterSpec> =
        SpecDelegateProvider { parameterSpecOf(it, type.name, *modifiers).also(::add) }

    public fun adding(
        type: KClass<*>,
        vararg modifiers: KModifier,
    ): SpecDelegateProvider<ParameterSpec> =
        SpecDelegateProvider { parameterSpecOf(it, type.name, *modifiers).also(::add) }
}

/**
 * Receiver for the `parameters` block providing an extended set of operators for the
 * configuration.
 */
@KotlinPoetDsl
public open class ParameterSpecHandlerScope private constructor(
    handler: ParameterSpecHandler,
    public override val owner: TypeSpecBuilder?
) :
    ParameterSpecHandler by handler {
    public inline operator fun String.invoke(
        type: TypeName,
        vararg modifiers: KModifier,
        configuration: ParameterSpecBuilder.() -> Unit,
    ): ParameterSpec = add(this, type, *modifiers, configuration = configuration)

    public inline operator fun String.invoke(
        type: Type,
        vararg modifiers: KModifier,
        configuration: ParameterSpecBuilder.() -> Unit,
    ): ParameterSpec = add(this, type, *modifiers, configuration = configuration)

    public inline operator fun String.invoke(
        type: KClass<*>,
        vararg modifiers: KModifier,
        configuration: ParameterSpecBuilder.() -> Unit,
    ): ParameterSpec = add(this, type, *modifiers, configuration = configuration)

    public companion object {
        public fun of(
            handler: ParameterSpecHandler,
            owner: TypeSpecBuilder?
        ): ParameterSpecHandlerScope =
            ParameterSpecHandlerScope(handler, owner)
    }
}

/** Wrapper of [ParameterSpec.Builder], providing DSL support as a replacement to Java builder. */
@KotlinPoetDsl
public class ParameterSpecBuilder(
    internal val name: String,
    internal val type: TypeName,
    private val nativeBuilder: ParameterSpec.Builder, public val owner: TypeSpecBuilder?
) {
    public fun addAsVal() {
        val own = owner
            ?: throw AssertionError("Can't add $name as a `val`, as it's in a top-level function")
        own.properties {
            add(
                this@ParameterSpecBuilder.name,
                this@ParameterSpecBuilder.type,
                *this@ParameterSpecBuilder.nativeBuilder.modifiers.toTypedArray()
            ) {
                setInitializer("%L", this@ParameterSpecBuilder.name)
            }
        }
    }

    public fun addAsVar() {
        val own = owner
            ?: throw AssertionError("Can't add $name as a `var`, as it's in a top-level function")
        own.properties {
            add(
                this@ParameterSpecBuilder.name,
                this@ParameterSpecBuilder.type,
                *this@ParameterSpecBuilder.nativeBuilder.modifiers.toTypedArray()
            ) {
                setInitializer("%L", this@ParameterSpecBuilder.name)
                isMutable = true
            }
        }
    }

    public val annotations: AnnotationSpecHandler =
        object : AnnotationSpecHandler {
            override fun add(annotation: AnnotationSpec) {
                annotationSpecs += annotation
            }
        }

    /** Invokes DSL to configure [AnnotationSpec] collection. */
    public inline fun annotations(configuration: AnnotationSpecHandlerScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        AnnotationSpecHandlerScope
            .of(annotations)
            .configuration()
    }

    public val modifiers: MutableList<KModifier> get() = nativeBuilder.modifiers
    public val kdoc: CodeBlock.Builder get() = nativeBuilder.kdoc
    public val tags: MutableMap<KClass<*>, *> get() = nativeBuilder.tags
    public val annotationSpecs: MutableList<AnnotationSpec> get() = nativeBuilder.annotations

    public fun addModifiers(vararg modifiers: KModifier) {
        this.modifiers += modifiers
    }

    public var defaultValue: CodeBlock
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.defaultValue(value)
        }

    public fun setDefaultValue(format: String, vararg args: Any) {
        defaultValue = codeBlockOf(format, *args)
    }

    public fun addKdoc(format: String, vararg args: Any) {
        nativeBuilder.addKdoc(format, *args)
    }

    public fun addKdoc(block: CodeBlock) {
        nativeBuilder.addKdoc(block)
    }

    public fun build(): ParameterSpec = nativeBuilder.build()
}
