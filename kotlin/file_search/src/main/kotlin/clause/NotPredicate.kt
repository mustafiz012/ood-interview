package clause

import filesystem.File

class NotPredicate(private val operand: Predicate) : CompositePredicate {
    override fun isMatch(inputFile: File): Boolean {
        return !operand.isMatch(inputFile)
    }
}
