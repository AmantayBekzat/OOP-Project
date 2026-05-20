package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;

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
        if (receiver == null || message == null) return;
        inbox.putIfAbsent(receiver, new ArrayList<>());
        inbox.get(receiver).add(message);
    }

    public List<Message> getMessages(User receiver) {
        if (receiver == null) return Collections.emptyList();
        return inbox.getOrDefault(receiver, new ArrayList<>());
    }

    public List<Message> getAllMessages() {
        List<Message> all = new ArrayList<>();
        for (List<Message> messages : inbox.values()) {
            all.addAll(messages);
        }
        return all;
    }

    public int countMessages(User user) {
        if (user == null) return 0;
        return inbox.getOrDefault(user, Collections.emptyList()).size();
    }
}
