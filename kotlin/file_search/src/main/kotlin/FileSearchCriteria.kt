import clause.Predicate
import filesystem.File

class FileSearchCriteria(private val predicate: Predicate) {
    fun isMatch(inputFile: File): Boolean = predicate.isMatch(inputFile)
}
