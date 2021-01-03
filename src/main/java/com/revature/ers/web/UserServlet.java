package com.revature.ers.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.revature.ers.controllers.UserController;


public class UserServlet extends HttpServlet {
	
	private static Logger log=Logger.getLogger(UserServlet.class);
	private UserController uc = new UserController();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
		resp.setContentType("application/json");
		resp.setStatus(404);
		final String URI = req.getRequestURI().replace("/project-1/user/", "");
		
		switch (URI) {
			case "login":
				uc.login(req, resp);
				break;
			case "create":
				uc.create(req,resp);
				break;
			}
			
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
	

}
