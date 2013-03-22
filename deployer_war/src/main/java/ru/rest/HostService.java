package ru.rest;

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
import ru.db.entities.Host;

@Path("hostService")
public class HostService {
	
    @GET
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Host create(Host obj) {
    	Host host = new Host();
    	host.setId(obj.getId());
    	host.setHostName(obj.getHostName());
    	host.setProfile(obj.getProfile());
    	
    	SQLiteConnector connection = new SQLiteConnector();
		IGenericDAO<Host> gen = new EventDAO<Host>(connection.getConnection());
		gen.insert(host);
		connection.close();
		return host;
	}
	
	@Path("/list")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Host> getList() {
		SQLiteConnector connection = new SQLiteConnector();
		IGenericDAO<Host> generic = new HostDAO<Host>(connection.getConnection());
		List<Host> result = generic.selectALL();
		connection.close();
		return result;
	}

}
