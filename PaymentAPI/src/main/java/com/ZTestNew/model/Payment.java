package com.ZTestNew.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.print.attribute.standard.MediaSize.Other;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Payment {
	
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/sliitpafs", "root", "root");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public String insertPayment(int doctorID, int hospitalID, int patientID, String total) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}

			String query = " insert into payments (`patientID`, `hospitalID`,`doctorID`,`total`, `date` )"
					+ " values (?, ?, ?, ?, ?)";

			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			System.out.println(formatter.format(date));

			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, patientID);
			preparedStmt.setInt(2, hospitalID);
			preparedStmt.setInt(3, doctorID);
			preparedStmt.setString(4, total);
			preparedStmt.setString(5, (formatter.format(date)));

			preparedStmt.execute();
			con.close();

			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the item.";
			System.err.println(e.getMessage());
		}
		return output;

	}
	
	public List<PaymentModel> readPayments() {
		List<PaymentModel> payementesList = new ArrayList<>();
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
//				return "Error while connecting to the database for reading.";
			}

			String query = "select * from payments";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String paymentID = Integer.toString(rs.getInt("id"));
				String patientID = Integer.toString(rs.getInt("patientID"));
				String hospitalID = Integer.toString(rs.getInt("hospitalID"));
				String doctorID = Integer.toString(rs.getInt("doctorID"));
				String total = rs.getString("total");
				String dates = rs.getString("date");

				PaymentModel paymodel = new PaymentModel();
				paymodel.setDates(dates);
				paymodel.setDoctorID(doctorID);
				paymodel.setHospitalID(hospitalID);
				paymodel.setPatientID(patientID);
				paymodel.setPaymentID(paymentID);
				paymodel.setTotal(total);

				payementesList.add(paymodel);

				System.out.println("lol" + paymentID + patientID);

			}

		} catch (Exception e) {

			System.err.println(e.getMessage() + "---------------------------------------------");
		}
		System.out.println(output);
		return payementesList;
	}
	
	



}