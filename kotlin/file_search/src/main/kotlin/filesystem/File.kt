package filesystem

import java.util.Collections

class File(
    val isDirectory: Boolean,
    val size: Int,
    val owner: String,
    val filename: String
) {
    private val entries: MutableSet<File> = mutableSetOf()

    fun extract(attributeName: FileAttribute): Any {
        return when (attributeName) {
            FileAttribute.SIZE -> size
            FileAttribute.OWNER -> owner
            FileAttribute.IS_DIRECTORY -> isDirectory
            FileAttribute.FILENAME -> filename
        }
    }

    fun addEntry(entry: File) {
        entries.add(entry)
    }

    fun getEntries(): Set<File> = Collections.unmodifiableSet(entries)
}
