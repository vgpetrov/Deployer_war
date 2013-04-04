package ru.rest;

import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ru.db.dao.EventDAO;
import ru.db.dao.HostDAO;
import ru.db.dao.IGenericDAO;
import ru.db.dao.SQLiteConnector;
import ru.db.entities.Event;
import ru.db.entities.Host;

@Path("eventService")
public class EventService {

    // curl -i -X GET -H 'Content-Type: application/json' -d '{"eventName": "install", "productName":"NSI",
    // "version":"02.008.00", "revision":"44220", "host":{"hostName":"newHost", "profile":"AppSrv02"}}'
    // http://localhost:8080/deployer_war/rest/eventService
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Event create(Event obj) {
        Event event = new Event();
        event.setEventDate(new Date());
        event.setEventName(obj.getEventName());
        event.setProductName(obj.getProductName());
        event.setVersion(obj.getVersion());
        event.setRevision(obj.getRevision());
        // event.setHost(obj.getHost());

        SQLiteConnector connection = new SQLiteConnector();
        IGenericDAO<Event> gen = new EventDAO(connection.getConnection());

        HostDAO hosts = new HostDAO(connection.getConnection());
        Host host = hosts.selectByNameAndProfile(obj.getHost().getHostName(), obj.getHost().getProfile());
        if (host == null) {
            hosts.insert(obj.getHost());
            host = hosts.selectByNameAndProfile(obj.getHost().getHostName(), obj.getHost().getProfile());
            event.setHost(host);
        } else {
            event.setHost(host);
        }

        gen.insert(event);
        connection.close();
        return event;
    }

    // curl -i -X GET http://localhost:8080/deployer_war/rest/eventService/list
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Event> getList() {
        SQLiteConnector connection = new SQLiteConnector();
        IGenericDAO<Event> generic = new EventDAO(connection.getConnection());
        List<Event> result = generic.selectALL();
        connection.close();
        return result;
    }

    // curl -i -X GET http://localhost:8080/deployer_war/rest/eventService/2
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Event> getAppList(@PathParam("id") Integer id) {
        SQLiteConnector connection = new SQLiteConnector();
        EventDAO eventDao = new EventDAO(connection.getConnection());
        List<Event> result = eventDao.selectApps(id);
        connection.close();
        return result;
    }

    // curl -i -X GET http://localhost:8080/deployer_war/rest/eventService/2/NSI
    @GET
    @Path("/{id}/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Event> getAppHistoryList(@PathParam("id") Integer id, @PathParam("name") String name) {
        SQLiteConnector connection = new SQLiteConnector();
        EventDAO eventDao = new EventDAO(connection.getConnection());
        List<Event> result = eventDao.selectAppsHistory(id, name);
        connection.close();
        return result;
    }

    // curl -i -X GET http://localhost:8080/deployer_war/rest/eventService/1/list
    @GET
    @Path("/{id}/list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Event> getHistoryList(@PathParam("id") Integer id) {
        SQLiteConnector connection = new SQLiteConnector();
        EventDAO eventDao = new EventDAO(connection.getConnection());
        List<Event> result = eventDao.selectHistory(id);
        connection.close();
        return result;
    }

}
