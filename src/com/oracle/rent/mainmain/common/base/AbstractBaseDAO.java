package com.oracle.rent.mainmain.common.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractBaseDAO {
    protected static final String driver = "oracle.jdbc.driver.OracleDriver";
    protected static final String url = "jdbc:oracle:thin:@192.168.0.18:1521:XE";
    protected static final String user = "C##user1";
    protected static final String pwd = "1234";

    public Connection conn = null; // DB연결에 사용되는 객체를 선언한다.
    protected PreparedStatement pstmt = null;
    protected ResultSet rs = null;

    public AbstractBaseDAO() {
        if (conn == null) {
            connDB();
        }
    }

    protected void connDB() {
        try {
            Class.forName(driver);
            System.out.println("Oracle 드라이버 로딩 성공");

            conn = DriverManager.getConnection(url, user, pwd);
            System.out.println("Connection 생성 성공");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // PreparedStatement를 사용하여 TO_DATE() 함수로 명시적으로 날짜 형식을 지정합니다.
    protected PreparedStatement prepareStatement(String sql) throws SQLException {
        return conn.prepareStatement(sql);
    }
}
