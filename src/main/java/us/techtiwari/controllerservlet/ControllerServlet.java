package us.techtiwari.controllerservlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import us.techtiwari.bean.UserDetailsBean;
import us.techtiwari.dao.DatabaseAccessObject;
import us.techtiwari.database.DatabaseConnection;
import us.techtiwari.transactionbean.TransactionBean;

public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DatabaseAccessObject dao;

	public void init(ServletConfig config) throws ServletException {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/userdetail";
		String username = "root";
		String password = "root@123";
		DatabaseConnection database = new DatabaseConnection(driver, url, username, password);
		this.dao = new DatabaseAccessObject(database);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			doprocess(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			doprocess(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void doprocess(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		String req_URI = request.getRequestURI();
		RequestDispatcher reqDispatcher = null;
		if (req_URI.endsWith("transactionDetail.do")) {
			ArrayList<TransactionBean> txdetail = (ArrayList<TransactionBean>) dao.txsList();
			request.setAttribute("txdetail", txdetail);
			request.getRequestDispatcher("/TransactionDetail.jsp").forward(request, response);

		}

		if (req_URI.endsWith("UserDetail.do")) {
			try {
				ArrayList<UserDetailsBean> userdetail = (ArrayList<UserDetailsBean>) dao.list();
				request.setAttribute("userdetail", userdetail);
				request.getRequestDispatcher("/ViewUserDetails.jsp").forward(request, response);
			} catch (SQLException e) {
				throw new ServletException("Cannot retrieve areas", e);
			}
		}

		if (req_URI.endsWith("Transfer.do")) {
			reqDispatcher = request.getRequestDispatcher("/FundTransfer.jsp");
			reqDispatcher.forward(request, response);

		}
		
			if (req_URI.endsWith("TransferMenu.do")) {
				reqDispatcher = request.getRequestDispatcher("/TransferMenu.html");
				reqDispatcher.forward(request, response);
				
			}

		if (req_URI.endsWith("processFund.do")) {
			String acc1 = request.getParameter("acc1");
			String acc2 = request.getParameter("acc2");
			String amt = request.getParameter("amt");
			String status = dao.checkUserAuthenication(acc1, acc2);
			if(status=="valid") {
			try {
				String statusTransfer = dao.tranferFund(acc1, acc2, amt);
				if (statusTransfer == "success") {
					RequestDispatcher rd = request.getRequestDispatcher("success.html");
					rd.forward(request, response);
				} else {
					RequestDispatcher rd = request.getRequestDispatcher("unsuccess.html");
					rd.forward(request, response);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			}
			else {
				RequestDispatcher rd = request.getRequestDispatcher("notexist.html");
				rd.forward(request, response);
			}

		}

	}


}
