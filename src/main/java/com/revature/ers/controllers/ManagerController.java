package com.revature.ers.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.exception.BusinessException;
import com.revature.ers.model.ErsReimbursment;
import com.revature.ers.model.ErsReimbursmentStatus;
import com.revature.ers.model.ErsUser;
import com.revature.ers.service.ManagerService;

public class ManagerController {

	private static Logger log = Logger.getLogger(ManagerController.class);

	private ObjectMapper om = new ObjectMapper();
	private ManagerService ms = new ManagerService();

	public void viewAllReimbursement(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		List<ErsReimbursment> listR;
		
		try {
			listR = ms.getAllRibs();
			String json = om.writeValueAsString(listR);
			resp.getWriter().print(json);
			resp.setStatus(200);
			log.info("<Request All Reimbursments Succmssfully!>");
		} catch (BusinessException e) {
			if(e.getMessage().equals("No Reimbursement Record Found in System")) {
				resp.setStatus(204);
			} else {
				resp.setStatus(500);
			}		
			resp.getWriter().print(e.getMessage());
			log.warn("<Request All Reimbursment Failed!>");
			log.warn(e.getMessage());
		}
			
	}

	public void changeRibStatusById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {	
		BufferedReader reader = req.getReader();
		StringBuilder sb = new StringBuilder();
		String line = reader.readLine();	
		while (line!=null) {
			sb.append(line);
			line = reader.readLine();
		}		
		String body = new String(sb);
		ErsReimbursment rib = om.readValue(body, ErsReimbursment.class);
		try {
			ms.changeRibStatusById(rib);
			resp.setStatus(200);
			resp.getWriter().print("The reimbursment has been updated successfully");
			log.info("<Update Reibursment Successfully!>");
		} catch (BusinessException e) {
			if(e.getMessage().equals("Internal error occured. Please contact customer service for more imformation")) {
				resp.setStatus(500);
			} else {
				resp.setStatus(404);
			}		
			resp.getWriter().print(e.getMessage());
			log.warn("<Update Reimbursment Failed!>");
			log.warn(e.getMessage());
		}	
	}
}
