package ru.db.entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Host {

    private Integer id;
    private String hostName;
    private String profile;
    private Integer webPort;
    private Integer adminPort;

    public Host() {
        super();
    }

    public Host(Integer id, String hostName, String profile, Integer webPort, Integer adminPort) {
        super();
        this.id = id;
        this.hostName = hostName;
        this.profile = profile;
        this.webPort = webPort;
        this.adminPort = adminPort;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public Integer getWebPort() {
        return webPort;
    }

    public void setWebPort(Integer webPort) {
        this.webPort = webPort;
    }

    public Integer getAdminPort() {
        return adminPort;
    }

    public void setAdminPort(Integer adminPort) {
        this.adminPort = adminPort;
    }

    @Override
    public String toString() {
        return "Host [id=" + id + ", hostName=" + hostName + ", profile=" + profile + ", webPort=" + webPort
                + ", adminPort=" + adminPort + "]";
    }

}
