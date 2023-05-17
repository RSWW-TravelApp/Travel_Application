package events.Saga;

import java.util.HashMap;
import java.util.Map;

public class ClientNotificationEvent {
    private String userId;
    private String message;
    private HashMap<String, String> properties;

    public ClientNotificationEvent(String userId, String message, HashMap<String, String> properties) {
        this.userId = userId;
        this.message = message;
        this.properties = properties;
    }

    public ClientNotificationEvent() {
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

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(HashMap<String, String> properties) {
        this.properties = properties;
    }
}
