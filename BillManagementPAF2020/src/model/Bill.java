package model;

import java.sql.*;

public class Bill {

	// A common method to connect to the DB
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/healthcaredb?autoReconnect=true&useSSL=false", "root", "root");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertBill(String PayMethod, String Amount, String pid) {

		String output = "";
		
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			
			// create a prepared statement
			/*String query = "INSERT  INTO bills('BillID','PayMethod','Amount','pid')" + "values(?, ?, ?, ?)";*/
			String query = "INSERT IGNORE INTO bills VALUES(?, ?, ?, ?)";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, PayMethod);
			preparedStmt.setDouble(3, Double.parseDouble(Amount));
			preparedStmt.setInt(4, Integer.parseInt(pid));
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			

			String newBills = readBill();
			output = "{\"status\":\"success\", \"data\": \"" +newBills+  "\"}";
			
			
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while inserting.\"}";
			
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readBill() {

		String output = "";

		try {
			Connection con = connect();
			if (con == null) {

				return "Error while connecting to the database for reading.";
			}

			// Prepare the HTML table to be displayed

			output = "<table border=\"2\"><tr><th>Payment Method</th><th>Amount</th><th>pid</th><th>Update</th><th>Remove</th></tr>";

			String query = "select * from bills";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set

			while (rs.next()) {
				String BillID = Integer.toString(rs.getInt("BillID"));
				String PayMethod = rs.getString("PayMethod");
				String Amount = Double.toString(rs.getDouble("Amount"));
				String pid = rs.getString("pid");

				// Add into the HTML table
				// possible problem here
				output += "<tr> <td> <input id='hidBillIDUpdate' name='hidBillIDUpdate' type='hidden' value=' " + BillID + "'>" + PayMethod + "</td>";output += "<td>" + Amount + "</td>"; output += "<td>" + pid + "</td>";
				
				//output += "<tr><td><input id='hidItemIDUpdate'name='hidItemIDUpdate' type='hidden'value='" + itemID + "'>" + itemCode + "</td>";output += "<td>" + itemName + "</td>";output += "<td>" + itemPrice + "</td>";output += "<td>" + itemDesc + "</td>";
				
				

				// buttons *****
				output +=  "<td><input name='btnUpdate' type='button'"+ "value='Update'"+"class='btnUpdate btn btn-secondary'></td>" 
				+"<td><input name='btnRemove' type='button'"+" value='Remove'"+"class='btnRemove btn btn-danger' data-billid='"
				+ BillID + "'>" + "</td></tr>"; 

				/*
				 output += "<td><input name='btnUpdate' type='button'"+ "value='Update'"+"class='btnUpdate btn btn-secondary'></td>"
				 +"<td><input name='btnRemove' type='button'"+" value='Remove'"+"class='btnRemove btn btn-danger' data-itemid='"
				 + itemID + "'>" + "</td></tr>";
				 * */
			}
			con.close();
			
			// Complete the HTML table
			output += "</table>";
			
		} catch (Exception e) {
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateBill(String BillID, String PayMethod, String Amount, String pid){
		
	 String output = "";
	 
	 try
	 {
	 Connection con = connect();
	 if (con == null){
		 return "Error while connecting to the database for updating."; 
		 
	 }
	 
	 // create a prepared statement
	 String query = "UPDATE bills SET PayMethod=?,Amount=?,pid=? WHERE BillID=?";
	 PreparedStatement preparedStmt = con.prepareStatement(query);
	 
	 // binding values
	 preparedStmt.setString(1, PayMethod);
	 preparedStmt.setDouble(2, Double.parseDouble(Amount));
	 preparedStmt.setInt(3, Integer.parseInt(pid));
	 preparedStmt.setInt(4, Integer.parseInt(BillID));
	 
	 // execute the statement
	 preparedStmt.execute();
	 con.close();
	 
	 String newBills = readBill();
	 output = "{\"status\":\"success\", \"data\": \"" +newBills+  "\"}";
	 
	 }
	 catch (Exception e)
	 {
	 output = "{\"status\":\"error\", \"data\": \"Error while updating \"}"; 
	 System.err.println(e.getMessage());
	 }
	 return output;
	 }

	public String deleteBill(String BillID) {

		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			
			String query = "delete from bills where BillID = ?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(BillID));
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newBills = readBill();
			output = "{\"status\":\"success\", \"data\": \"" +newBills+ "\"}";
			
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while deleting \"}"; 
			System.err.println(e.getMessage());
		}
		return output;
	}

}
