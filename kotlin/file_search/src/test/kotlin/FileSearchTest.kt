import clause.AndPredicate
import clause.SimplePredicate
import filesystem.File
import filesystem.FileAttribute
import operator.EqualsOperator
import operator.RegexMatchOperator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FileSearchTest {
    @Test
    fun testFileSearch() {
        println("\n=== Testing File Search System ===")

        println("\n--- Creating File System Structure ---")
        val root = File(true, 0, "adam", "root")
        val a = File(false, 2000, "adam", "a")
        val b = File(false, 3000, "george", "b")
        println("✓ Created root directory: 'root' (owner: adam, size: 0)")
        println("✓ Created file 'a': (owner: adam, size: 2000, isDirectory: false)")
        println("✓ Created file 'b': (owner: george, size: 3000, isDirectory: false)")

        root.addEntry(a)
        root.addEntry(b)
        println("✓ Added files 'a' and 'b' to root directory")

        println("\n--- Building Search Criteria ---")
        println("✓ Creating AND predicate with two conditions:")
        println("  1. File must NOT be a directory (isDirectory = false)")
        println("  2. Owner name must match regex pattern 'ge.*' (starts with 'ge')")

        val criteria = FileSearchCriteria(
            AndPredicate(
                listOf(
                    SimplePredicate(
                        FileAttribute.IS_DIRECTORY,
                        EqualsOperator(),
                        false
                    ),
                    SimplePredicate(
                        FileAttribute.OWNER,
                        RegexMatchOperator(),
                        "ge.*"
                    )
                )
            )
        )
        println("✓ Search criteria created successfully")

        println("\n--- Executing File Search ---")
        val fileSearch = FileSearch()
        val result = fileSearch.search(root, criteria)
        println("✓ Search completed")
        println("✓ Found " + result.size + " file(s) matching criteria")

        println("\n--- Analyzing Results ---")
        println("✓ Expected: Only file 'b' should match because:")
        println("  - File 'a': owner='adam' (doesn't match 'ge.*')")
        println("  - File 'b': owner='george' (matches 'ge.*') and is not a directory")

        assertEquals(1, result.size)
        assertEquals("b", result[0].filename)
        println("✓ Result verified: Found 1 file")
        println("✓ File name verified: 'b'")
        println("=== File Search Test Completed Successfully ===\n")
    }
}
