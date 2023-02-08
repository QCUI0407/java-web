import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCDemo {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/student";
        String username = "root";
        String password = "codeup";
        Connection conn = DriverManager.getConnection(url, username, password);

        String sql = "INSERT INTO student (id, name, gender,birthday,score,email,tel,status) VALUES ('1','aaa','M',1999-12-12,8.8,'asd@qq.com',12345678,1);";

        Statement stmt = conn.createStatement();

        stmt.execute(sql);
    }
}
