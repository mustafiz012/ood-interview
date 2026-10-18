package operator

fun interface ComparisonOperator<T> {
    fun isMatch(attributeValue: T, expectedValue: T): Boolean
}
