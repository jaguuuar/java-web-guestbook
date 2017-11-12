package model;

public class GuestModel {

    private String message;
    private String name;
    private String messageDate;

    public GuestModel(String message, String name, String messageDate){

        this.message = message;
        this.name = name;
        this.messageDate = messageDate;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }

    public String getMessageDate() {
        return messageDate;
    }

}
