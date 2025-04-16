import java.sql.DriverManager

fun main() {
    val url = "jdbc:sqlite:sample.db"
    DriverManager.getConnection(url).use { conn ->
        val stmt = conn.createStatement()
        stmt.execute("CREATE TABLE IF NOT EXISTS test (id INTEGER PRIMARY KEY, name TEXT)")
        stmt.execute("INSERT INTO test(name) VALUES ('Jakub')")
        val rs = stmt.executeQuery("SELECT * FROM test")
        while (rs.next()) {
            println("ID: ${rs.getInt("id")}, Name: ${rs.getString("name")}")
        }
    }
}
