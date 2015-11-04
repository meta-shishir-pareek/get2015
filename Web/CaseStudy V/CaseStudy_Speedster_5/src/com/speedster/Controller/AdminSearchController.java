package com.speedster.Controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.speedster.db.helper.VehicleDAO;
import com.speedster.exception.CarDekhoSystemException;
import com.speedster.fascade.SpeedsterFascade;
import com.speedster.model.Vehicle;
import com.speedster.service.SpeedsterService;

/**
 * Servlet implementation class AdminSearchController
 */
@WebServlet("/AdminSearchController")
public class AdminSearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminSearchController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session.getAttribute("currentSessionUser")==null) {
			response.sendRedirect("/login.jsp");
		}
		else{
			SpeedsterService speedsterService = new SpeedsterService();
			List<Vehicle> vehicleList=null;
			try {
				vehicleList = speedsterService.getVehicleBrands();
				request.setAttribute("brandList", vehicleList);
			} catch (CarDekhoSystemException e) {
				e.printStackTrace();
			}
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/AdminSearch.jsp");
			requestDispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session.getAttribute("currentSessionUser")==null) {
			response.sendRedirect("/login.jsp");
		}
		else{
			List<Vehicle> vehicleList = getVehicleList(request);
			request.setAttribute("vehicleList", vehicleList);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/AdminSearchResult.jsp");
			requestDispatcher.forward(request, response);
		}
	}

	/**
	 * 
	 * @param request
	 * @return vehicleList 
	 */
	private List<Vehicle> getVehicleList(HttpServletRequest request){
		String selectedCatagory;
		String filter = null;
		List<Vehicle> vehicleList=null;
		SpeedsterService speedsterService = new SpeedsterService();
		filter = request.getParameter("filter");
		try {
			if(filter!=null){
				if(filter.equals("byBudget")) {
					selectedCatagory=request.getParameter("budget");
					if(selectedCatagory == null) {
						return null;
					}		
					vehicleList = speedsterService.getVehiclesByBudget(selectedCatagory);
				}
				else {
					selectedCatagory=request.getParameter("brand");
					vehicleList = speedsterService.getVehiclesByBrand(selectedCatagory);
				}
			}
			else{
				vehicleList = speedsterService.getAllVehicle();
			}
		} catch (CarDekhoSystemException e) {
			e.printStackTrace();
		}
		return vehicleList;
	}
}