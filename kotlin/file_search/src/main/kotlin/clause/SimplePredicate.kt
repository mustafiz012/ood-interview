package clause

import filesystem.File
import filesystem.FileAttribute
import operator.ComparisonOperator

class SimplePredicate<T : Any>(
    private val attributeName: FileAttribute,
    private val operator: ComparisonOperator<T>,
    val expectedValue: T
) : Predicate {
    @Suppress("UNCHECKED_CAST")
    override fun isMatch(inputFile: File): Boolean {
        val actualValue = inputFile.extract(attributeName)
        return if (expectedValue::class.java.isInstance(actualValue)) {
            operator.isMatch(actualValue as T, expectedValue)
        } else {
            false
        }
    }
}
