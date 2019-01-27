package jdbc;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcMain {

	public static void main(String[] args) throws SQLException, IOException {
		String uri = "jdbc:postgresql://172.28.33.11:15432/sdddb";
		Connection conn = DriverManager.getConnection(uri, "testuser", "password");
		Statement st = conn.createStatement();

		// insert
		PreparedStatement pstmt = conn.prepareStatement("insert into byte_table(data) values( ? )");
		pstmt.setBytes(1, Files.readAllBytes(new File("A:\\tmp\\10m.zip").toPath()));
		pstmt.execute();

		// select
		//		ResultSet resultSet = st.executeQuery("select * from byte_table");
		//		while (resultSet.next()) {
		//			byte[] bytes = resultSet.getBytes("data");
		//			System.out.println(bytes);
		//		}

		st.close();
		conn.close();
	}
}