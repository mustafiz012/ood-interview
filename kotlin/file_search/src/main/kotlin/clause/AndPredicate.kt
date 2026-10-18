package clause

import filesystem.File

class AndPredicate(private val operands: List<Predicate>) : CompositePredicate {
    override fun isMatch(inputFile: File): Boolean {
        return operands.all { it.isMatch(inputFile) }
    }
}
