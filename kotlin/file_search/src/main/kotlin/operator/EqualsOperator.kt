package operator

import java.util.Objects

class EqualsOperator<T> : ComparisonOperator<T> {
    override fun isMatch(attributeValue: T, expectedValue: T): Boolean {
        return Objects.equals(attributeValue, expectedValue)
    }
}
