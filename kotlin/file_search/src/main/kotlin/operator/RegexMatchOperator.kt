package operator

import java.util.regex.Pattern

class RegexMatchOperator : ComparisonOperator<String> {
    override fun isMatch(attributeValue: String, expectedValue: String): Boolean {
        val pattern = Pattern.compile(expectedValue)
        return pattern.matcher(attributeValue).matches()
    }
}
