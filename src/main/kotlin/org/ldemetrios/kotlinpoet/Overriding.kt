@file:Suppress("ClassName")

package org.ldemetrios.kotlinpoet

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.TypeName

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
    public fun Map(k: TypeName, v: TypeName, config: overriding.Map.() -> Unit) {
        with(Map(k, v), config)
    }

    public data class Map(val k: TypeName, val v: TypeName) {
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

    public fun Any(config: overriding.Any.() -> Unit) {
        with(overriding.Any, config)
    }

    public object Any {
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
}
