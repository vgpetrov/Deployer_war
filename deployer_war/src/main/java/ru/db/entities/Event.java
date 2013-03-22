package ru.db.entities;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Event {
	
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	
	private Date eventDate;
	private String eventName;
	private String productName;
	private String version;
	private Host host;
	
	public Event() {
		super();
	}

	public Event(Date eventDate, String eventName, String productName,
			String version, Host host) {
		super();
		this.eventDate = eventDate;
		this.eventName = eventName;
		this.productName = productName;
		this.version = version;
		this.host = host;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Host getHost() {
		return host;
	}

	public void setHost(Host host) {
		this.host = host;
	}

	@Override
	public String toString() {
		return "Events [eventDate=" + dateFormat.format(eventDate) + ", eventName=" + eventName
				+ ", productName=" + productName + ", version=" + version
				+ ", host=" + host.toString() + "]";
	}

}
