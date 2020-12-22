package com.revature.ers.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.revature.ers.controllers.EmployeeController;

public class EmployeeServlet extends HttpServlet {

	private static Logger log=Logger.getLogger(EmployeeServlet.class);
	private EmployeeController ec = new EmployeeController();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		resp.setStatus(404);
		final String URI = req.getRequestURI().replace("/project-1/emp/", "");
		if(req.getSession(false)==null) {
			log.warn("Submit Reimbursment Failed. User's Credential is not found");
			resp.setStatus(401);
			resp.getWriter().print("Unauthorized User");
		} else {
			switch (URI) {
			case "submit":
				ec.submitReimbursement(req, resp);		
				break;
			case "view-all":
				ec.viewAllReimbursement(req, resp);			
				break;		
			}
		}	

		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

		
}
