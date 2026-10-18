package operator

class LessThanOperator<T : Number> : ComparisonOperator<T> {
    override fun isMatch(attributeValue: T, expectedValue: T): Boolean {
        return attributeValue.toDouble() < expectedValue.toDouble()
    }
}
