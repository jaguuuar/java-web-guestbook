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

    @Override
    public String toString() {

        String guestString = "<p style=\"background-color:powderblue\"><p1><b>" + this.message + "</b></p1><br>" +
                "<p2>Name: </p2>" + "<p3><b>" + this.name + "</b></p3><br>" +
                "<p4>Date: </p4>" + "<p5>" + this.messageDate + "<p/5></p>";

        return  guestString;
    }

}
