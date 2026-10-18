import filesystem.File
import java.util.ArrayDeque

class FileSearch {
    fun search(root: File, criteria: FileSearchCriteria): List<File> {
        val result = mutableListOf<File>()
        val recursionStack = ArrayDeque<File>()
        recursionStack.add(root)

        while (recursionStack.isNotEmpty()) {
            val next = recursionStack.pop()
            if (criteria.isMatch(next)) {
                result.add(next)
            }
            for (entry in next.getEntries()) {
                recursionStack.push(entry)
            }
        }
        return result
    }
}
