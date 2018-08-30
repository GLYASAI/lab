package org.abewang.jdbc;

import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @Author Abe
 * @Date 2018/8/29.
 */
public class JDBCQuickGuide {

    public static void main(String[] args) throws Exception {
        // STEP 1: Register JDBC driver
        Class.forName("com.mysql.jdbc.Driver");

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Step 2: Open a connection
            conn = DriverManager.getConnection("jdbc:mysql://abe-ubuntu:3306/test",
                    "abe", "abewang");
            // STEP 3: Execute a query
            stmt = conn.createStatement();
            String sql = "select * from user";
            rs = stmt.executeQuery(sql);

            //STEP 4: Extract data from result set
            while (rs.next()) {
                int id = rs.getInt("id");
                String email = rs.getString("email");
                String name = rs.getString("name");
                System.out.println("id: " + id);
                System.out.println("email: " + email);
                System.out.println("name: " + name);
                System.out.println();
            }
        } finally {
            //STEP 6: Clean-up environment
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
}
