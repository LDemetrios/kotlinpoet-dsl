[![CircleCI](https://img.shields.io/circleci/build/gh/hanggrian/kotlinpoet-dsl)](https://app.circleci.com/pipelines/github/hanggrian/kotlinpoet-dsl/)
[![Codecov](https://img.shields.io/codecov/c/gh/hanggrian/kotlinpoet-dsl)](https://app.codecov.io/gh/hanggrian/kotlinpoet-dsl/)
[![Java](https://img.shields.io/badge/java-8+-informational)](https://docs.oracle.com/javase/8/)

# Why fork?

There are a few updates to the library I would like to add, which require updating existent types. After I do, I'll update README and doc further. Here are brief plans:

- Add incremental configuration possibility:
  ```kt
  primaryConstructor {
      line1
      line2
  }

  primaryConstructor {
      line3
      line4
  }
  ```
  instead of only
  ```kt
  setPrimaryConstructor {
      line1
      line2
      line3
      line4
  }
  ```
- Add some operator overloads
- Add special functions for overriding popular methods from popular interfaces
- Add a possibility to add val/var constructor parameters easily.

# KotlinPoet DSL

Lightweight Kotlin extension of [KotlinPoet](https://github.com/square/kotlinpoet/),
providing Kotlin DSL functionality and other convenient solutions.

- Full of convenient methods to achieve minimum code writing possible.
- Options to invoke DSL. For example, `functions.add("main") { ... }` is as good
  as `functions { "main" { ... } }`. Scroll down for more information.
- Smooth transition, existing KotlinPoet native specs can still be configured
  with DSL.

```kt
buildFileSpec("com.example.helloworld", "HelloWorld") {
    types.addClass("HelloWorld") {
        addModifiers(PUBLIC, FINAL)
        functions {
            "main" {
                addModifiers(PUBLIC, STATIC)
                returns = UNIT
                parameters.add("args", STRING.array)
                appendLine("%T.out.println(%S)", System::class, "Hello, KotlinPoet!")
            }
        }
    }
}.writeTo(System.out)
```

## Download

```gradle
repositories {
    mavenCentral()
}
dependencies {
    implementation "com.hanggrian:kotlinpoet-dsl:$version"
}
```

## Usage

### Use `T::class` as parameters

`KClass<*>` can now be used as format arguments. There is also inline reified
type function whenever possible.

```kt
buildFunSpec("sortList") {
    returns = INT
    parameters.add(classNamed("java.util", "List").parameterizedBy(hoverboard), "list")
    appendLine("%T.sort(list)", Collections::class)
    appendLine("return list")
}

buildPropertySpec("count", INT) {
    initializer("%L", 0)
}
```

### Optional DSL

Some elements (field, method, parameter, etc.) are wrapped in container class.
These containers have ability to add components with/without invoking DSL.

For example, 2 examples below will produce the same result.

```kt
types {
    addClass("Car") {
        functions {
            "getWheels" {
                returns = INT
                appendLine("return wheels")
            }
            "setWheels" {
                parameters {
                    add("wheels", INT)
                }
                appendLine("this.wheels = wheels")
            }
        }
    }
}

types.addClass("Car") {
    functions.add("getWheels") {
        returns = INT
        appendLine("return wheels")
    }
    functions.add("setWheels") {
        paraneters.add("wheels", INT)
        appendLine("this.wheels = wheels")
    }
}
```

### Property delegation

In spirit of [Gradle Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html#using_kotlin_delegated_properties),
creating a spec can be done by delegating to a property.

```kt
val title by parameters.adding(String::class) {
    annotations.add<NotNull>()
}

val message by parameters.adding(String::class) {
    annotation.add<Nullable>()
}
```

### Fluent TypeName API

Write `TypeName` and all its subtypes fluently.

```kt
val myClass: ClassName =
    classNamed("com.example", "MyClass")

val listener: LambdaTypeName =
    STRING.lambdaBy(returns = UNIT)

val memberOfString: MemberTypeName =
    myClass.memberOf("myField")

val pairOfInteger: ParameterizedTypeName =
    Pair::class.name.parameterizedBy(Int::class, Int::class)

val tVariable: TypeVariableName =
    "T".generics

val producerOfCharSequence: WildcardTypeName =
    CharSequence::class.name.producerOf()
```
