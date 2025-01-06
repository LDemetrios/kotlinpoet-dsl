@file:JvmMultifileClass
@file:JvmName("TypeNamesKt")

package org.ldemetrios.kotlinpoet

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import java.lang.reflect.Type

public inline fun TypeName.nullable(): TypeName = copy(nullable = true)

public fun TypeName.annotate(vararg annotations: AnnotationSpec): TypeName =
    copy(annotations = annotations.toList())

public inline val Type.name: TypeName get() = asTypeName()


public val UNSAFE_VARIANCE: AnnotationSpec = annotationSpecOf(ClassName("kotlin", "UnsafeVariance"))

public val PAIR: ClassName = ClassName("kotlin", "Pair")
public val TRIPLE: ClassName = ClassName("kotlin", "Triple")
public val COLLECTION_ITERATOR: ClassName = ClassName("kotlin.collections", "Iterator")
public val LIST_ITERATOR: ClassName = ClassName("kotlin.collections", "ListIterator")

/*
// \n((expect|open|annotation|data|fun|sealed|abstract|value) )*(class|interface|object|enum) [^\n]*


public val Annotation : ClassName = ClassName("kotlin", "Annotation")
public val Any : ClassName = ClassName("kotlin", "Any")
public val ArithmeticException : ClassName = ClassName("kotlin", "ArithmeticException")
public val Array : ClassName = ClassName("kotlin", "Array")
public val ArrayIndexOutOfBoundsException : ClassName = ClassName("kotlin", "ArrayIndexOutOfBoundsException")
public val AssertionError : ClassName = ClassName("kotlin", "AssertionError")
public val AutoCloseable : ClassName = ClassName("kotlin", "AutoCloseable")
public val Boolean : ClassName = ClassName("kotlin", "Boolean")
public val BooleanArray : ClassName = ClassName("kotlin", "BooleanArray")
public val Byte : ClassName = ClassName("kotlin", "Byte")
public val ByteArray : ClassName = ClassName("kotlin", "ByteArray")
public val Char : ClassName = ClassName("kotlin", "Char")
public val CharArray : ClassName = ClassName("kotlin", "CharArray")
public val CharSequence : ClassName = ClassName("kotlin", "CharSequence")
public val ClassCastException : ClassName = ClassName("kotlin", "ClassCastException")
public val Comparable : ClassName = ClassName("kotlin", "Comparable")
public val Comparator : ClassName = ClassName("kotlin", "Comparator")
public val ConcurrentModificationException : ClassName = ClassName("kotlin", "ConcurrentModificationException")
public val DeepRecursiveFunction : ClassName = ClassName("kotlin", "DeepRecursiveFunction")
public val DeepRecursiveScope : ClassName = ClassName("kotlin", "DeepRecursiveScope")
public val DeprecationLevel : ClassName = ClassName("kotlin", "DeprecationLevel")
public val Double : ClassName = ClassName("kotlin", "Double")
public val DoubleArray : ClassName = ClassName("kotlin", "DoubleArray")
public val Enum : ClassName = ClassName("kotlin", "Enum")
public val Error : ClassName = ClassName("kotlin", "Error")
public val Exception : ClassName = ClassName("kotlin", "Exception")
public val Float : ClassName = ClassName("kotlin", "Float")
public val FloatArray : ClassName = ClassName("kotlin", "FloatArray")
public val Function : ClassName = ClassName("kotlin", "Function")
public val IllegalArgumentException : ClassName = ClassName("kotlin", "IllegalArgumentException")
public val IllegalStateException : ClassName = ClassName("kotlin", "IllegalStateException")
public val IndexOutOfBoundsException : ClassName = ClassName("kotlin", "IndexOutOfBoundsException")
public val Int : ClassName = ClassName("kotlin", "Int")
public val IntArray : ClassName = ClassName("kotlin", "IntArray")
public val KotlinVersion : ClassName = ClassName("kotlin", "KotlinVersion")
public val Lazy : ClassName = ClassName("kotlin", "Lazy")
public val LazyThreadSafetyMode : ClassName = ClassName("kotlin", "LazyThreadSafetyMode")
public val Long : ClassName = ClassName("kotlin", "Long")
public val LongArray : ClassName = ClassName("kotlin", "LongArray")
public val NoSuchElementException : ClassName = ClassName("kotlin", "NoSuchElementException")
public val Nothing : ClassName = ClassName("kotlin", "Nothing")
public val NotImplementedError : ClassName = ClassName("kotlin", "NotImplementedError")
public val NoWhenBranchMatchedException : ClassName = ClassName("kotlin", "NoWhenBranchMatchedException")
public val NullPointerException : ClassName = ClassName("kotlin", "NullPointerException")
public val Number : ClassName = ClassName("kotlin", "Number")
public val NumberFormatException : ClassName = ClassName("kotlin", "NumberFormatException")
public val OutOfMemoryError : ClassName = ClassName("kotlin", "OutOfMemoryError")
public val Pair : ClassName = ClassName("kotlin", "Pair")
public val Result : ClassName = ClassName("kotlin", "Result")
public val RuntimeException : ClassName = ClassName("kotlin", "RuntimeException")
public val Short : ClassName = ClassName("kotlin", "Short")
public val ShortArray : ClassName = ClassName("kotlin", "ShortArray")
public val String : ClassName = ClassName("kotlin", "String")
public val Throwable : ClassName = ClassName("kotlin", "Throwable")
public val Triple : ClassName = ClassName("kotlin", "Triple")
public val UByte : ClassName = ClassName("kotlin", "UByte")
public val UByteArray : ClassName = ClassName("kotlin", "UByteArray")
public val UInt : ClassName = ClassName("kotlin", "UInt")
public val UIntArray : ClassName = ClassName("kotlin", "UIntArray")
public val ULong : ClassName = ClassName("kotlin", "ULong")
public val ULongArray : ClassName = ClassName("kotlin", "ULongArray")
public val UninitializedPropertyAccessException : ClassName = ClassName("kotlin", "UninitializedPropertyAccessException")
public val Unit : ClassName = ClassName("kotlin", "Unit")
public val UnsupportedOperationException : ClassName = ClassName("kotlin", "UnsupportedOperationException")
public val UShort : ClassName = ClassName("kotlin", "UShort")
public val UShortArray : ClassName = ClassName("kotlin", "UShortArray")

public val BuilderInference : AnnotationSpec = annotationSpecOf(ClassName("kotlin", " BuilderInference"))
public val ConsistentCopyVisibility : AnnotationSpec = annotationSpecOf(ClassName("kotlin", " ConsistentCopyVisibility"))
public val ContextctionTypeParams : AnnotationSpec = annotationSpecOf(ClassName("kotlin", " ContextctionTypeParams"))
public val Deprecated : AnnotationSpec = annotationSpecOf(ClassName("kotlin", " Deprecated"))
public val DeprecatedSinceKotlin : AnnotationSpec = annotationSpecOf(ClassName("kotlin", " DeprecatedSinceKotlin"))
public val DslMarker : AnnotationSpec = annotationSpecOf(ClassName("kotlin", " DslMarker"))
public val EagerInitialization : AnnotationSpec = annotationSpecOf(ClassName("kotlin", " EagerInitialization"))
public val ExperimentalMultiplatform : AnnotationSpec = annotationSpecOf(ClassName("kotlin", " ExperimentalMultiplatform"))
public val ExperimentalStdlibApi : AnnotationSpec = annotationSpecOf(ClassName("kotlin", " ExperimentalStdlibApi"))
public val ExperimentalSubclassOptIn : AnnotationSpec = annotationSpecOf(ClassName("kotlin", " ExperimentalSubclassOptIn"))
public val ExperimentalUnsignedTypes : AnnotationSpec = annotationSpecOf(ClassName("kotlin", " ExperimentalUnsignedTypes"))
public val ExposedCopyVisibility : AnnotationSpec = annotationSpecOf(ClassName("kotlin", " ExposedCopyVisibility"))
public val ExtensionctionType : AnnotationSpec = annotationSpecOf(ClassName("kotlin", " ExtensionctionType"))
public val Js : AnnotationSpec = annotationSpecOf(ClassName("kotlin", " Js"))
public val Meta : AnnotationSpec = annotationSpecOf(ClassName("kotlin", " Meta"))
public val OptIn : AnnotationSpec = annotationSpecOf(ClassName("kotlin", " OptIn"))
public val Optionalation : AnnotationSpec = annotationSpecOf(ClassName("kotlin", " Optionalation"))
public val OverloadResolutionByLambdaReturnType : AnnotationSpec = annotationSpecOf(ClassName("kotlin", "OverloadResolutionByLambdaReturnType"))
public val ParameterName : AnnotationSpec = annotationSpecOf(ClassName("kotlin", " ParameterName"))
public val PublishedApi : AnnotationSpec = annotationSpecOf(ClassName("kotlin", " PublishedApi"))
public val ReplaceWith : AnnotationSpec = annotationSpecOf(ClassName("kotlin", " ReplaceWith"))
public val RequiresOptIn : AnnotationSpec = annotationSpecOf(ClassName("kotlin", " RequiresOptIn"))
public val SinceKotlin : AnnotationSpec = annotationSpecOf(ClassName("kotlin", " SinceKotlin"))
public val SubclassOptInRequired : AnnotationSpec = annotationSpecOf(ClassName("kotlin", " SubclassOptInRequired"))
public val Suppress : AnnotationSpec = annotationSpecOf(ClassName("kotlin", " Suppress"))
public val Throws : AnnotationSpec = annotationSpecOf(ClassName("kotlin", " Throws"))
public val UnsafeVariance : AnnotationSpec = annotationSpecOf(ClassName("kotlin", " UnsafeVariance"))
*/
