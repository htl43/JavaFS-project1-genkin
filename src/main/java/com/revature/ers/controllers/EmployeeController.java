package com.revature.ers.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.exception.BusinessException;
import com.revature.ers.model.ErsReimbursment;
import com.revature.ers.model.ErsUser;
import com.revature.ers.service.EmployeeService;

public class EmployeeController {
	
	private static Logger log=Logger.getLogger(EmployeeController.class);
	
	private ObjectMapper om = new ObjectMapper();
	private EmployeeService es = new EmployeeService();

	public void submitReimbursement(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if(req.getMethod().equals("POST")) {		
			BufferedReader reader = req.getReader();
			StringBuilder sb = new StringBuilder();
			String line = reader.readLine();
			
			while (line!=null) {
				sb.append(line);
				line = reader.readLine();
			}
			
			String body = new String(sb);
			ErsReimbursment ersReimbursement = om.readValue(body, ErsReimbursment.class);
	
			try {
				es.submitErsReibursment(ersReimbursement);		
				resp.setStatus(200);
				resp.getWriter().print("The reimbursment has been submitted successfully");
				log.info("<Submited Reibursment Successfully!>");
				log.info(ersReimbursement);	
			} catch (BusinessException e) {
				HttpSession ses = req.getSession(false);
				if (ses != null) {
					ses.invalidate();
				}
				resp.setStatus(500);
				resp.getWriter().print(e.getMessage());
				log.warn("<Submited Reibursment Failed!>");
				log.warn(e.getMessage());
			}	
		} else {
			HttpSession ses = req.getSession(false);
			if (ses != null) {
				ses.invalidate();
			}
			resp.setStatus(400);
			resp.getWriter().print("Bad Requested Using Http Method");
			log.warn("<Submited Reibursment Failed!>");
			log.warn("Bad Requested Using Http Method");
		}
		
		
	}

	public void viewAllReimbursement(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession ses = req.getSession();	
		ErsUser ersUser = (ErsUser)ses.getAttribute("user");
		List<ErsReimbursment> listR;
		try {
			listR = es.getRibByEmpId(ersUser.getUserId());		
			String json = om.writeValueAsString(listR);
			resp.getWriter().print(json);
			resp.setStatus(200);
			log.info("<Request All Reimbursment Successfully!>");		
		} catch (BusinessException e) {
			if(e.getMessage().equals("No Reimbursment Record Found for This User")) {
				resp.setStatus(204);
			} else {
				resp.setStatus(500);
			}	
			resp.getWriter().print(e.getMessage());
			log.warn("<Request All Reimbursment Failed!>");
			log.warn(e.getMessage());
		}	
	}

	
}
