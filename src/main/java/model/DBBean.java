package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class DBBean {
	private static DBBean instance = new DBBean();
	
	public static DBBean getInstance() {
		return instance;
	}
	
	private DBBean() {}
	
	private Connection getConnection() throws Exception {
        Context initCtx = new InitialContext();
        Context envCtx = (Context) initCtx.lookup("java:comp/env");
        DataSource ds = (DataSource)envCtx.lookup("jdbc/basicjsp");
        return ds.getConnection();
    }
	
	public void insertMember(MemberBean member) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "";
		
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			conn = DriverManager.getConnection("jdbc:mysql://yaknman.cafe24.com/yaknman", "yaknman", "dksgfnzy!1");
			
			sql = "insert into member values(?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getName());
			pstmt.setString(2, member.getUser_id());
			pstmt.setString(3, member.getUser_pw());
			pstmt.setString(4, member.getPhone());
			pstmt.setString(5, member.getEmail());
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
            if (pstmt != null) try { pstmt.clearParameters(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
	}
	
	public int userCheck(String user_id, String user_pw) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			conn = DriverManager.getConnection("jdbc:mysql://yaknman.cafe24.com/yaknman", "yaknman", "dksgfnzy!1");
			
			String sql = "select user_id, user_pw "
					   + "from member "
					   + "where user_id = ? and user_pw = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user_id);
			pstmt.setString(2, user_pw);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(rs.getString("user_id").equals(user_id)) {
					if(rs.getString("user_pw").equals(user_pw)) {
						
						return 1;
					}
				}
			}
			
			return 0;
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
            if (pstmt != null) try { pstmt.clearParameters(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
            if (rs != null) try {rs.close();}catch(SQLException ex) {}
        }
		return 0;
	}
	
	public MemberBean getSession(String user_id, String user_pw) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberBean member = new MemberBean();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			conn = DriverManager.getConnection("jdbc:mysql://yaknman.cafe24.com/yaknman", "yaknman", "dksgfnzy!1");
			
			String sql = "select user_id, name, email from member where user_id = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, user_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				member.setUser_id(rs.getString("user_id"));
				member.setName(rs.getString("name"));
				member.setEmail(rs.getString("email"));
				
				return member;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
            if (pstmt != null) try { pstmt.clearParameters(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
            if (rs != null) try {rs.close();}catch(SQLException ex) {}
        }
		return null;
	}
	

	public void getLocation(UserLocation location) {
		UserLocation locationInfo = new UserLocation();
		
		locationInfo.setLatitude(location.getLatitude());
		locationInfo.setLongitude(location.getLongitude());
	}
	
	
}
