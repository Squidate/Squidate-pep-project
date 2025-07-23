package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;   

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    private AccountService accountService = new AccountService();
    private MessageService messageService = new MessageService();
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);

        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);

        app.post("/messages", this::createMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessagesByIdHandler);
        app.get("/messages/{account_id}/messages", this::getMessagesByUserIdHandler);
        app.get("/accounts/{account_id}/messages", this::getMessagesByUserIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void registerHandler(Context ctx){
        Account account = ctx.bodyAsClass(Account.class);
        if (account.getUsername() == null || account.getPassword() == null || account.getUsername().isEmpty() || account.getPassword().length() < 4) {
        ctx.status(400);
        return;
    }
    Account registered = accountService.register(account);
    if (registered == null){
        ctx.status(400);
    } else{
        ctx.status(200).json(registered);
    }
    }
    private void loginHandler(Context ctx){
        Account account = ctx.bodyAsClass(Account.class);
        if (account.getUsername() == null || account.getPassword() == null || account.getUsername().isEmpty() || account.getPassword().isEmpty()) {
        ctx.status(400);
        return;
        }
        Account loggedIn = accountService.login(account);
        if (loggedIn == null){
        ctx.status(401);
    } else{
        ctx.status(200).json(loggedIn);
    }

    }

    private void getMessagesByUserIdHandler(Context ctx){
        int accountId = Integer.parseInt(ctx.pathParam("account_id"));
        ctx.status(200).json(messageService.getMessageByUserId(accountId));
    }

    private void createMessageHandler(Context ctx){
        Message message = ctx.bodyAsClass(Message.class);
        if(message.getMessage_text() == null || message.getMessage_text().isEmpty() || message.getMessage_text().length() >= 255 || message.getPosted_by() == 0){
            ctx.status(400);
            return;
        }
        Message created = messageService.createMessage(message);
        if (created == null){
            ctx.status(400);
        } else {
            ctx.status(200).json(created);
        }

    }

    private void deleteMessageHandler(Context ctx){
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));

        Message deleted = messageService.deleteMessageById(messageId);
        if (deleted == null){
            ctx.status(200).json("");
        } else{
            ctx.status(200).json(deleted);
        }
    }

    private void getMessagesByIdHandler(Context ctx){
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);
        if(message == null){
            ctx.status(200).json("");
        } else{
            ctx.status(200).json(message);
        }
    }

    private void updateMessageHandler(Context ctx){
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = ctx.bodyAsClass(Message.class);
        if(message.getMessage_text() == null || message.getMessage_text().isEmpty() || message.getMessage_text().length() >= 255){
            ctx.status(400);
            return;
        }   

        Message updated = messageService.updateMessage(messageId, message.getMessage_text());
        if(updated == null){
            ctx.status(400);
        } else{
            ctx.status(200).json(updated);
        }


    }

    private void getAllMessagesHandler(Context ctx){
        ctx.json(messageService.getAllMessages());
    }

}