package ru.rest;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import ru.db.dao.EventDAO;
import ru.db.dao.HostDAO;
import ru.db.dao.IGenericDAO;
import ru.db.dao.SQLiteConnector;
import ru.db.dao.UserDAO;
import ru.db.entities.Event;
import ru.db.entities.Host;
import ru.db.entities.SecuredEvent;
import ru.db.entities.User;

@Path("securedService")
public class SecuredService {
    
    private static final Logger logger = Logger.getLogger(SecuredService.class);
    
    private String getMd5Hash(String input) {
        String md5 = null;
        if (null == input)
            return null;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(input.getBytes(), 0, input.length());
            md5 = new BigInteger(1, digest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
        }
        return md5;
    }
    
    
    // curl -i -X POST -H 'Content-Type: application/json' -d '{"event":{"eventName": "install", "productName":"NSI","version":"02.008.00", "revision":"44220", "host":{"hostName":"newHost", "profile":"AppSrv02","webPort":"9081", "adminPort":"9061"}},"user":{"login":"admin","password":"123"}}' http://localhost:8080/deployer_war/rest/securedService
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Event create(SecuredEvent secured) {
        SQLiteConnector connection = new SQLiteConnector();
        UserDAO userDao = new UserDAO(connection.getConnection());
        Event result = null;
        User user = secured.getUser();
        user.setPassword(getMd5Hash(user.getPassword()));
        
        if (userDao.checkUser(user)) {
            Event obj = secured.getEvent();
            Event event = new Event();
            event.setEventDate(new Date());
            event.setEventName(obj.getEventName());
            event.setProductName(obj.getProductName());
            event.setVersion(obj.getVersion());
            event.setRevision(obj.getRevision());

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
            result = event;
        }
        
        return result;
    }
    
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Event> getList() {
        return null;
    }

}
