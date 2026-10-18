package clause

import filesystem.File

class OrPredicate(private val operands: List<Predicate>) : CompositePredicate {
    override fun isMatch(inputFile: File): Boolean {
        return operands.any { it.isMatch(inputFile) }
    }
}
