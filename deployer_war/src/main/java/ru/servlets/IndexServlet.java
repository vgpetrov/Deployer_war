package ru.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import ru.db.dao.EventDAO;
import ru.db.dao.HostDAO;
import ru.db.dao.IGenericDAO;
import ru.db.dao.SQLiteConnector;
import ru.db.entities.Event;
import ru.db.entities.Host;

/**
 * Servlet implementation class IndexServlet
 */
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		SQLiteConnector connection = new SQLiteConnector();
//		IGenericDAO<Host> generic = new HostDAO<Host>(connection.getConnection());
//		generic.insert(new Host(3, "SAS", "ssad"));
//		List<Host> hosts = generic.selectALL();
//		for (Host hostt : hosts) {
//			System.out.println(hostt.toString());
//		}
//		connection.close();
		
//		IGenericDAO<Host> generic = new HostDAO<Host>();
//		generic.insert(new Host("SAS", "ssad"));
//		
//		List<Host> hosts = generic.selectALL();
//		for (Host hostt : hosts) {
//			System.out.println(hostt.toString());
//		}
		
//		IGenericDAO<Event> generic = new EventDAO<Event>();
//		Event ev = new Event();
//		ev.setEventDate(new java.sql.Date((new Date()).getTime()));
//		ev.setEvent("install");
//		ev.setProductName("NSI");
//		ev.setVersion("44889");
//		ev.setHostRef("localhost");
//		generic.insert(ev);
//		
//		List<Event> events = generic.selectALL();
//		for (Event eve : events) {
//			System.out.println(eve.toString());
//		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
