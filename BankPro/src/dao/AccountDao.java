package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import acc.Account;
import acc.Acount;
import acc.SpecialAccount;

public class AccountDao extends DBConnection {
	public int insertAccount(Acount acc) {
		String sql = "insert into bank (id, name, balance, grade) values(?, ?, ?, ?)";
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		int cnt = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, acc.getId());
			pstmt.setString(2, acc.getName());
			pstmt.setInt(3, acc.getBalance());
			if (acc instanceof SpecialAccount) {
				pstmt.setString(4, ((SpecialAccount) acc).getGrade());
			} else {
				pstmt.setString(4, null);
			}
			cnt = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(conn);
		}
		return cnt;
	}
	
	public int updateBalance(Acount acc) {
		String sql = "update bank set balance=? where id=?";
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		int cnt = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, acc.getBalance());
			pstmt.setString(2, acc.getId());
			cnt = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(conn);
		}
		return cnt;
	}
	
	public Acount selectAccount(String id) {
		String sql = "select id, name, balance, grade from bank where id=?";
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Acount acc = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rset = pstmt.executeQuery();
			if (rset != null && rset.next()) {
				String grade = rset.getString("grade");
				if (grade != null) {
					acc = new SpecialAccount(
						rset.getString("id"),
						rset.getString("name"),
						rset.getInt("balance"),
						grade
					);
				} else {
					acc = new Acount(
						rset.getString("id"),
						rset.getString("name"),
						rset.getInt("balance")
					);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
			close(conn);
		}
		return acc;
	}
	
	public List<Acount> selectAccountList() {
		String sql = "select id, name, balance, grade from bank";
		Connection conn = getConnection();
		Statement stmt = null;
		ResultSet rset = null;
		List<Acount> list = new ArrayList<>();
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(sql);
			if (rset != null) {
				while (rset.next()) {
					String grade = rset.getString("grade");
					Acount acc;
					if (grade != null) {
						acc = new SpecialAccount(
							rset.getString("id"),
							rset.getString("name"),
							rset.getInt("balance"),
							grade
						);
					} else {
						acc = new Acount(
							rset.getString("id"),
							rset.getString("name"),
							rset.getInt("balance")
						);
					}
					list.add(acc);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(stmt);
			close(conn);
		}
		return list;
	}
}
