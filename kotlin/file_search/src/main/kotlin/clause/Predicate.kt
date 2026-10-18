package clause

import filesystem.File

fun interface Predicate {
    fun isMatch(inputFile: File): Boolean
}
