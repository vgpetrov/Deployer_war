package ru.rest;

import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ru.db.dao.EventDAO;
import ru.db.dao.HostDAO;
import ru.db.dao.IGenericDAO;
import ru.db.dao.SQLiteConnector;
import ru.db.entities.Event;

@Path("eventService")
public class EventService {
	
	// curl -i -X GET -H 'Content-Type: application/json' -d '{"eventDate": "", "eventName": "install", "productName":"NSI", "version":"44115", host:{"id":"1", "hostName":"vasya" ,"profile":"petya"}}' http://localhost:8080/deployer_war/rest/eventService
    @GET
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Event create(Event obj) {
		Event event = new Event();
		event.setEventDate(new Date());
		event.setEventName(obj.getEventName());
		event.setProductName(obj.getProductName());
		event.setVersion(obj.getVersion());
		event.setHost(obj.getHost());
		
		SQLiteConnector connection = new SQLiteConnector();
		IGenericDAO<Event> gen = new EventDAO<Event>(connection.getConnection());
		gen.insert(event);
		connection.close();
		return event;
	}

	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Event> getList() {
		SQLiteConnector connection = new SQLiteConnector();
		IGenericDAO<Event> generic = new EventDAO<Event>(connection.getConnection());
		List<Event> result = generic.selectALL();
		connection.close();
		return result;
	}

}
