package com.revature.ers.controllers;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.exception.BusinessException;
import com.revature.ers.model.ErsUser;
import com.revature.ers.model.LoginDTO;
import com.revature.ers.service.UserService;


public class UserController {
	
	private static Logger log=Logger.getLogger(UserController.class);
	
	private ObjectMapper om = new ObjectMapper();
	private UserService us = new UserService();

	public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {	
			om.configure(Feature.AUTO_CLOSE_SOURCE, true);
			BufferedReader reader = req.getReader();
			StringBuilder sb = new StringBuilder();
			String line = reader.readLine();
			
			while (line!=null) {
				sb.append(line);
				line = reader.readLine();
			}
			
			String body = new String(sb);
			LoginDTO lDTO = om.readValue(body, LoginDTO.class);
			
			try {
				ErsUser ersUser = us.login(lDTO.username, lDTO.password);
				ersUser.setPassword(""); // send empty password to frontend
				String json = om.writeValueAsString(ersUser);
				resp.getWriter().print(json);
				resp.setStatus(200);		
				HttpSession ses = req.getSession();	
				ses.setAttribute("user", ersUser);
				ses.setAttribute("loggedin", true);
				log.info("<Login Succesfull!>");
				log.info(ersUser.toString());
			} catch (BusinessException e) {
				HttpSession ses = req.getSession(false);
				if (ses != null) {
					ses.invalidate();
				}
				resp.setStatus(401);
				resp.getWriter().print(e.getMessage());
				log.warn("<Login Failed!>");
				log.warn(e.getMessage());
			}			
	}

	public void create(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		om.configure(Feature.AUTO_CLOSE_SOURCE, true);
		if(req.getMethod().equals("POST")) {		
			BufferedReader reader = req.getReader();
			StringBuilder sb = new StringBuilder();
			String line = reader.readLine();
			
			while (line!=null) {
				sb.append(line);
				line = reader.readLine();
			}
			
			String body = new String(sb);
			ErsUser ersUser = om.readValue(body, ErsUser.class);
			ersUser.setPassword(us.encryptPassword(ersUser.getPassword()));
			try {
				us.create(ersUser);		
				resp.setStatus(200);
				resp.getWriter().print("Account has been created successfully");
				log.info("<Create Account Successfully!>");
				log.info(ersUser);			
			} catch (BusinessException e) {
				HttpSession ses = req.getSession(false);
				if (ses != null) {
					ses.invalidate();
				}
				resp.setStatus(500);
				resp.getWriter().print(e.getMessage());
				log.warn("<Create Account Failed!>");
				log.warn(e.getMessage());
			}	
		} else {
			HttpSession ses = req.getSession(false);
			if (ses != null) {
				ses.invalidate();
			}
			resp.setStatus(400);
			resp.getWriter().print("Bad Requested Using Http Method");
			log.warn("<Created Account Failed!>");
			log.warn("Bad Requested Using Http Method");	
		}
	}

}
