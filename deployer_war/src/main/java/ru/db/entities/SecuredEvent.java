package ru.db.entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SecuredEvent {

    private Event event;
    private User user;

    public SecuredEvent() {
        super();
    }

    public SecuredEvent(Event event, User user) {
        super();
        this.event = event;
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
