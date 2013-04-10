package ru.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ru.db.dao.HostDAO;
import ru.db.dao.IGenericDAO;
import ru.db.dao.SQLiteConnector;
import ru.db.entities.Host;

@Path("hostService")
public class HostService {

    // curl -i -X GET -H 'Content-Type: application/json' -d '{"hostName":"newHost","profile":"AppSrv03","webPort":"9081", "adminPort":"9061"}' http://localhost:8080/deployer_war/rest/hostService
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Host create(Host obj) {
        Host host = new Host();
        host.setHostName(obj.getHostName());
        host.setProfile(obj.getProfile());
        host.setWebPort(obj.getWebPort());
        host.setAdminPort(obj.getAdminPort());

        SQLiteConnector connection = new SQLiteConnector();
        IGenericDAO<Host> gen = new HostDAO(connection.getConnection());
        gen.insert(host);
        connection.close();
        return host;
    }

    // curl -i -X GET http://localhost:8080/deployer_war/rest/hostService/list
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Host> getList() {
        SQLiteConnector connection = new SQLiteConnector();
        IGenericDAO<Host> generic = new HostDAO(connection.getConnection());
        List<Host> result = generic.selectALL();
        connection.close();
        return result;
    }

}
