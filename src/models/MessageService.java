package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageService {
    private static MessageService instance;
    private Map<User, List<Message>> inbox;

    private MessageService(){
        inbox = new HashMap<>();
    }

    public static MessageService getInstance() {
        if(instance == null){
            instance = new MessageService();
        }
        return instance;
    }

    public void send(User receiver, Message message) {
        inbox.putIfAbsent(receiver, new ArrayList<>());
        inbox.get(receiver).add(message);
    }

    public List<Message> getMessages(User receiver) {
        return inbox.getOrDefault(receiver, new ArrayList<>());
    }
}
