@file:Suppress("ClassName")

package org.ldemetrios.kotlinpoet

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.TypeName
import java.util.function.IntFunction
import java.util.stream.IntStream

public fun TypeSpecBuilder.overrideProp(
    name: String,
    type: TypeName,
    config: PropertySpecBuilder.() -> Unit
): Unit = properties {
    name(type, OVERRIDE, configuration = config)
}

public data class InternalParameterRepr(
    val name: String,
    val type: TypeName,
    val modifiers: Array<out KModifier>,
    val configuration: ParameterSpecBuilder.() -> Unit,
)

public operator fun String.invoke(
    type: TypeName,
    vararg modifiers: KModifier,
    configuration: ParameterSpecBuilder.() -> Unit = {},
): InternalParameterRepr = InternalParameterRepr(this, type, modifiers, configuration)

public fun TypeSpecBuilder.overrideFun(
    name: String,
    returnType: TypeName,
    vararg params: InternalParameterRepr,
    config: FunSpecBuilder.() -> Unit
): Unit = functions {
    name {
        addModifiers(OVERRIDE)
        returns = returnType
        parameters {
            for (p in params) {
                p.name(p.type, *p.modifiers, configuration = p.configuration)
            }
        }
        config()
    }
}


public object overriding {
    // TODO We'll need to use codegen here I guess...
    public fun Map(k: TypeName, v: TypeName, config: overriding.MapImplHelper.() -> Unit) {
        with(MapImplHelper(k, v), config)
    }

    public data class MapImplHelper(val k: TypeName, val v: TypeName) {
        public fun TypeSpecBuilder.entries(config: PropertySpecBuilder.() -> Unit) {
            overrideProp("entries", SET(MAP_ENTRY(k, v)), config)
        }

        public fun TypeSpecBuilder.keys(config: PropertySpecBuilder.() -> Unit) {
            overrideProp("keys", SET(k), config)
        }

        public fun TypeSpecBuilder.values(config: PropertySpecBuilder.() -> Unit) {
            overrideProp("values", COLLECTION(v), config)
        }

        public fun TypeSpecBuilder.size(config: PropertySpecBuilder.() -> Unit) {
            overrideProp("size", INT, config)
        }

        public fun TypeSpecBuilder.containsKey(config: FunSpecBuilder.() -> Unit) {
            overrideFun("containsKey", BOOLEAN, "key"(k), config = config)
        }

        public fun TypeSpecBuilder.containsValue(config: FunSpecBuilder.() -> Unit) {
            overrideFun("containsValue", BOOLEAN, "value"(v), config = config)
        }

        public fun TypeSpecBuilder.get(config: FunSpecBuilder.() -> Unit) {
            overrideFun("get", v.nullable(), "key"(k), config = config)
        }

        public fun TypeSpecBuilder.getOrDefault(config: FunSpecBuilder.() -> Unit) {
            overrideFun("getOrDefault", v, "key"(k) {}, "defaultValue"(v) {}, config = config)
        }

        public fun TypeSpecBuilder.isEmpty(config: FunSpecBuilder.() -> Unit) {
            overrideFun("isEmpty", BOOLEAN, config = config)
        }

        public fun TypeSpecBuilder.forEach(config: FunSpecBuilder.() -> Unit) {
            overrideFun("forEach", UNIT, "action"(BI_CONSUMER(-k, -v)), config = config)
        }
    }

    public fun Any(config: overriding.AnyImplHelper.() -> Unit) {
        with(overriding.AnyImplHelper, config)
    }

    public object AnyImplHelper {
        public fun TypeSpecBuilder.equal(config: FunSpecBuilder.() -> Unit) {
            overrideFun("equals", BOOLEAN, "other"(ANY.nullable()), config = config)
        }

        public fun TypeSpecBuilder.hashCode(config: FunSpecBuilder.() -> Unit) {
            overrideFun("hashCode", INT, config = config)
        }

        public fun TypeSpecBuilder.toString(config: FunSpecBuilder.() -> Unit) {
            overrideFun("toString", STRING, config = config)
        }
    }

    public fun CharSequence(config: overriding.CharSequenceImplHelper.() -> Unit) {
        with(overriding.CharSequenceImplHelper, config)
    }

    public object CharSequenceImplHelper {
        public fun TypeSpecBuilder.length(config: PropertySpecBuilder.() -> Unit) {
            overrideProp("length", INT, config)
        }

        public fun TypeSpecBuilder.chars(config: FunSpecBuilder.() -> Unit) {
            overrideFun("chars", IntStream::class.asTypeName(), config = config)
        }

        public fun TypeSpecBuilder.codePoints(config: FunSpecBuilder.() -> Unit) {
            overrideFun("codePoints", IntStream::class.asTypeName(), config = config)
        }

        public fun TypeSpecBuilder.isEmpty(config: FunSpecBuilder.() -> Unit) {
            overrideFun("isEmpty", BOOLEAN, config = config)
        }

        public fun TypeSpecBuilder.get(config: FunSpecBuilder.() -> Unit) {
            overrideFun("get", CHAR, "index"(INT), config = config)
        }

        public fun TypeSpecBuilder.subSequence(config: FunSpecBuilder.() -> Unit) {
            overrideFun(
                "subSequence",
                CHAR_SEQUENCE,
                "startIndex"(INT), "endIndex"(INT),
                config = config
            )
        }
    }

    public fun Comparable(t: TypeName, config: overriding.ComparableImplHelper.() -> Unit) {
        with(overriding.ComparableImplHelper(t), config)
    }

    public data class ComparableImplHelper(val t: TypeName) {
        public fun TypeSpecBuilder.compareTo(config: FunSpecBuilder.() -> Unit) {
            overrideFun("compareTo", INT, "other"(t), config = config)
        }
    }

    public fun List(e: TypeName, config: overriding.ListImplHelper.() -> Unit) {
        with(overriding.ListImplHelper(e), config)
    }

    public data class ListImplHelper(val e: TypeName) {
        public fun TypeSpecBuilder.forEach(config: FunSpecBuilder.() -> Unit) {
            overrideFun("forEach", UNIT, "action"(CONSUMER(-e)), config = config)
        }

        public fun TypeSpecBuilder.iterator(config: FunSpecBuilder.() -> Unit) {
            overrideFun("iterator", ITERATOR(e), config = config)
        }

        public fun TypeSpecBuilder.spliterator(config: FunSpecBuilder.() -> Unit) {
            overrideFun("spliterator", SPLITERATOR(e), config = config)
        }

        public fun TypeSpecBuilder.size(config: PropertySpecBuilder.() -> Unit) {
            overrideProp("size", INT, config = config)
        }

        public fun TypeSpecBuilder.contains(config: FunSpecBuilder.() -> Unit) {
            overrideFun("contains", BOOLEAN, "element"(e), config = config)
        }

        public fun TypeSpecBuilder.containsAll(config: FunSpecBuilder.() -> Unit) {
            overrideFun("containsAll", BOOLEAN, "elements"(COLLECTION(e)), config = config)
        }

        public fun TypeSpecBuilder.get(config: FunSpecBuilder.() -> Unit) {
            overrideFun("get", e, "index"(INT), config = config)
        }

        public fun TypeSpecBuilder.getFirst(config: FunSpecBuilder.() -> Unit) {
            overrideFun("getFirst", e, config = config)
        }

        public fun TypeSpecBuilder.getLast(config: FunSpecBuilder.() -> Unit) {
            overrideFun("getLast", e, config = config)
        }

        public fun TypeSpecBuilder.indexOf(config: FunSpecBuilder.() -> Unit) {
            overrideFun("indexOf", INT, "element"(e), config = config)
        }

        public fun TypeSpecBuilder.isEmpty(config: FunSpecBuilder.() -> Unit) {
            overrideFun("isEmpty", BOOLEAN, config = config)
        }

        public fun TypeSpecBuilder.lastIndexOf(config: FunSpecBuilder.() -> Unit) {
            overrideFun("lastIndexOf", INT, "element"(e), config = config)
        }

        public fun TypeSpecBuilder.listIterator(config: FunSpecBuilder.() -> Unit) {
            overrideFun("listIterator", LIST_ITERATOR(e), config = config)
        }

        public fun TypeSpecBuilder.listIteratorByIndex(config: FunSpecBuilder.() -> Unit) {
            overrideFun("listIterator", LIST_ITERATOR(e), "index"(INT), config = config)
        }

        public fun TypeSpecBuilder.reversed(config: FunSpecBuilder.() -> Unit) {
            overrideFun("reversed", MUTABLE_LIST(e), config = config)
        }

        public fun TypeSpecBuilder.subList(config: FunSpecBuilder.() -> Unit) {
            overrideFun("subList", LIST(e), "fromIndex"(INT), "toIndex"(INT), config = config)
        }

        public fun TypeSpecBuilder.parallelStream(config: FunSpecBuilder.() -> Unit) {
            overrideFun("parallelStream", STREAM(e), config = config)
        }

        public fun TypeSpecBuilder.stream(config: FunSpecBuilder.() -> Unit) {
            overrideFun("stream", STREAM(e), config = config)
        }

        public fun TypeSpecBuilder.toArray(config: FunSpecBuilder.() -> Unit) {
            functions {
                "toArray" {
                    addModifiers(OVERRIDE)
                    val t = TypeVariableName("T")
                    addTypeVariables(t)
                    parameters {
                        for (p in arrayOf("generator"(INT_FUNCTION(ARRAY(t))))) {
                            p.name(p.type, *p.modifiers, configuration = p.configuration)
                        }
                    }
                    returns = ARRAY(t)
                    config()
                }
            }
        }
    }
}

//
//@field:kotlin.jvm.JvmField public val ITERABLE: com.squareup.kotlinpoet.ClassName /* compiled code */
//
//@field:kotlin.jvm.JvmField public val COLLECTION: com.squareup.kotlinpoet.ClassName /* compiled code */
//
//@field:kotlin.jvm.JvmField public val LIST: com.squareup.kotlinpoet.ClassName /* compiled code */
//
//@field:kotlin.jvm.JvmField public val SET: com.squareup.kotlinpoet.ClassName /* compiled code */
//
//@field:kotlin.jvm.JvmField public val MUTABLE_ITERABLE: com.squareup.kotlinpoet.ClassName /* compiled code */
//
//@field:kotlin.jvm.JvmField public val MUTABLE_COLLECTION: com.squareup.kotlinpoet.ClassName /* compiled code */
//
//@field:kotlin.jvm.JvmField public val MUTABLE_LIST: com.squareup.kotlinpoet.ClassName /* compiled code */
//
//@field:kotlin.jvm.JvmField public val MUTABLE_SET: com.squareup.kotlinpoet.ClassName /* compiled code */
//
//@field:kotlin.jvm.JvmField public val MUTABLE_MAP: com.squareup.kotlinpoet.ClassName /* compiled code */
//
//@field:kotlin.jvm.JvmField public val MUTABLE_MAP_ENTRY: com.squareup.kotlinpoet.ClassName /* compiled code */
//
//@field:kotlin.jvm.JvmField public val ENUM: com.squareup.kotlinpoet.ClassName /* compiled code */
//
