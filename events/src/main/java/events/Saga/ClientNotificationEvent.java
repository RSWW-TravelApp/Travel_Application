package events.Saga;

import java.util.HashMap;
import java.util.List;

public class ClientNotificationEvent {
    private String userId;
    private String message;
    private String type;
    private List<String> groups;
    private HashMap<String, Object> properties;

    public ClientNotificationEvent(String userId, String message, String type, List<String> groups, HashMap<String, Object> properties) {
        this.userId = userId;
        this.message = message;
        this.type = type;
        this.groups = groups;
        this.properties = properties;
    }

    public ClientNotificationEvent() {
    }

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HashMap<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(HashMap<String, Object> properties) {
        this.properties = properties;
    }
}
