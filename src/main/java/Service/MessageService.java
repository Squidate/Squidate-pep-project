package Service;

import DAO.MessageDAO;
import Model.Message;
import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService(){
        this.messageDAO = new MessageDAO();
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int id){
        return messageDAO.getMessageById(id);
    }

    public Message createMessage(Message message){
        return messageDAO.insertMessage(message);
    }

    public Message deleteMessageById(int messageId){
        return messageDAO.deleteMessageById(messageId);
    }

    public Message updateMessage(int messageId, String newText){
        return messageDAO.updateMessage(messageId, newText);

    }

    public List<Message> getMessageByUserId(int userId){
        return messageDAO.getMessagesByUserId(userId);
    }
    
}
