package ru.db.entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Host {

	private Integer id;
	private String hostName;
	private String profile;
	
	public Host() {
		super();
	}

	public Host(Integer id, String hostName, String profile) {
		super();
		this.id = id;
		this.hostName = hostName;
		this.profile = profile;
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

	@Override
	public String toString() {
		return "HostServer [id=" + id + ", hostName=" + hostName + ", profile="
				+ profile + "]";
	}
	
}
