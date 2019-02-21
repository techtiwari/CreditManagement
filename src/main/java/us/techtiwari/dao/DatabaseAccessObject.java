package us.techtiwari.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import us.techtiwari.bean.UserDetailsBean;
import us.techtiwari.database.DatabaseConnection;
import us.techtiwari.transactionbean.TransactionBean;

public class DatabaseAccessObject {
	private DatabaseConnection database;

	public DatabaseAccessObject(DatabaseConnection database) {
		this.database = database;
	}

	public ArrayList<UserDetailsBean> list() throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		ArrayList<UserDetailsBean> details = new ArrayList<UserDetailsBean>();

		try {
			connection = database.getConnection();
			statement = connection.prepareStatement("select * from user");
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				UserDetailsBean userdetail = new UserDetailsBean();
				userdetail.setAccNo(resultSet.getString("id"));
				userdetail.setName(resultSet.getString("name"));
				userdetail.setEmail(resultSet.getString("email"));
				userdetail.setBalance(resultSet.getString("balance"));
				details.add(userdetail);
			}
		} finally {
			if (resultSet != null)
				try {
					resultSet.close();
				} catch (SQLException logOrIgnore) {
				}
			if (statement != null)
				try {
					statement.close();
				} catch (SQLException logOrIgnore) {
				}
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException logOrIgnore) {
				}
		}

		return details;
	}

	public String checkUserAuthenication(String acc1, String acc2) throws SQLException {
		String accNo1 = acc1;
		String accNo2 = acc2;
		String status = "";
		Connection connection = database.getConnection();
		try {
			PreparedStatement pst1 = connection.prepareStatement("select id from user where id='" + accNo1 + "'");
			ResultSet rs1 = pst1.executeQuery();
			boolean b1 = rs1.next();
			PreparedStatement pst2 = connection.prepareStatement("select id from user where id='" + accNo2 + "'");
			ResultSet rs2 = pst2.executeQuery();
			boolean b2 = rs2.next();
			if (b1 == true && b2 == true) {
				status = "valid";
				System.out.println("validated");
			} else {
				status = "invalid";
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return status;

	}

	public String tranferFund(String acc1, String acc2, String amt1) throws SQLException {
		String accNo1 = acc1;
		String accNo2 = acc2;
		String amt = amt1;
		System.out.println(amt);
		float amount = Float.parseFloat(amt);
		System.out.println(amount);
		String status = "";
		Connection connection = database.getConnection();
		try {
			PreparedStatement pst1 = connection.prepareStatement(" select balance from user where id='" + accNo1 + "'");
			ResultSet rs1 = pst1.executeQuery();
			PreparedStatement pst2 = connection.prepareStatement(" select balance from user where id='" + accNo2 + "'");
			ResultSet rs2 = pst2.executeQuery();
			float bal1, bal2;

			while (rs1.next() && rs2.next()) {
				bal1 = Float.parseFloat(rs1.getString(1));
				System.out.println("balance 1" + bal1);
				bal2 = Float.parseFloat(rs2.getString(1));
				System.out.println("balance 1" + bal2);
				if (bal1 > amount) {
					float fromBal = bal1 - amount;
					float ToBal = bal2 + amount;
					PreparedStatement pstUpdateFrom = connection
							.prepareStatement("UPDATE user SET balance=? where id=?");

					pstUpdateFrom.setFloat(1, fromBal);
					pstUpdateFrom.setString(2, accNo1);
					int rowcount1 = pstUpdateFrom.executeUpdate();
					PreparedStatement pstUpdateTo = connection.prepareStatement("UPDATE user SET balance=? where id=?");
					pstUpdateTo.setFloat(1, ToBal);
					pstUpdateTo.setString(2, accNo2);
					int rowcount2 = pstUpdateTo.executeUpdate();
					if (rowcount1 == 1 && rowcount2 == 1) {
						System.out.println("transfered");
					}
					PreparedStatement pstFromName = connection
							.prepareStatement("select name from user where id='" + accNo1 + "'");
					ResultSet rsFromName = pstFromName.executeQuery();
					String fromName = "";
					while (rsFromName.next()) {
						fromName = rsFromName.getString(1);

						PreparedStatement pstToName = connection
								.prepareStatement("select name from user where id='" + accNo2 + "'");
						ResultSet rsToName = pstToName.executeQuery();
						String toName = "";
						while (rsToName.next()) {
							toName = rsToName.getString(1);

							PreparedStatement txFrom = connection.prepareStatement(
									"insert into transaction(accountNo,name,amount,status)values(?,?,?,?)");
							txFrom.setString(1, accNo1);
							txFrom.setString(2, fromName);
							txFrom.setFloat(3, amount);
							txFrom.setString(4, "debited");
							int rowCount3 = txFrom.executeUpdate();
							PreparedStatement txTo = connection.prepareStatement(
									"insert into transaction(accountNo,name,amount,status)values(?,?,?,?)");
							txFrom.setString(1, accNo2);
							txFrom.setString(2, toName);
							txFrom.setFloat(3, amount);
							txFrom.setString(4, "credited");

							int rowCount4 = txFrom.executeUpdate();
							if (rowCount3 == 1 && rowCount4 == 1) {
								status = "success";
								System.out.println("Transfered successfully");

							}
						}
					}

				} else {
					status = "Unsuccess";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;

	}

	public ArrayList<TransactionBean> txsList() throws SQLException {

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		ArrayList<TransactionBean> txdetails = new ArrayList<TransactionBean>();

		try {
			connection = database.getConnection();
			statement = connection.prepareStatement("select * from transaction");
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				TransactionBean txdetail = new TransactionBean();
				txdetail.setAccountno(resultSet.getString("accountno"));
				txdetail.setName(resultSet.getString("name"));
				txdetail.setBal(resultSet.getString("amount"));
				txdetail.setStatus(resultSet.getString("status"));
				txdetails.add(txdetail);
			}
		} finally {
			if (resultSet != null)
				try {
					resultSet.close();
				} catch (SQLException logOrIgnore) {
				}
			if (statement != null)
				try {
					statement.close();
				} catch (SQLException logOrIgnore) {
				}
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException logOrIgnore) {
				}
		}

		return txdetails;

	}


}
