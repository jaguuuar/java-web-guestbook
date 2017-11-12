package web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.GuestDAO;
import model.GuestModel;

import java.io.*;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Form implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String response = "";
        String method = httpExchange.getRequestMethod();

        ArrayList<GuestModel> book = GuestDAO.getBook();
        String bookString = this.getBookString(book);

        String form = "<form method=\"POST\">\n" +
                "  Message<br>\n" +
                "  <input type=\"text\" name=\"message\" value=\"Msg\">\n" +
                "  <br><br>\n" +
                "  Name<br>\n" +
                "  <input type=\"text\" name=\"name\" value=\"Cris\">\n" +
                "  <br><br>\n" +
                "  <input type=\"submit\" value=\"Submit\">\n" +
                "</form> ";

        // Send a form if it wasn't submitted yet.
        if(method.equals("GET")){
            response = "<html><body>" +
                    "<h1>Simple Guestbook</h1>" +
                    bookString +
                    form +
                    "</body></html>";
        }

        // If the form was submitted, retrieve it's content.
        if(method.equals("POST")){
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();

            System.out.println(formData);
            Map inputs = parseFormData(formData);
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            Date date = new Date();

            String guestMessage = inputs.get("message") + "";
            String guestName = inputs.get("name") + "";
            String messageDate = dateFormat.format(date) + "";

            GuestDAO.insertMessage(guestMessage, guestName, messageDate);

            book = GuestDAO.getBook();
            bookString = this.getBookString(book);

            response = "<html><body>" +
                    "<h1>Simple Guestbook</h1>" +
                    bookString +
                    form +
                    "</body><html>";
        }

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    /**
     * web.Form data is sent as a urlencoded string. Thus we have to parse this string to get data that we want.
     * See: https://en.wikipedia.org/wiki/POST_(HTTP)
     */
    private static Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for(String pair : pairs){
            String[] keyValue = pair.split("=");
            // We have to decode the value because it's urlencoded. see: https://en.wikipedia.org/wiki/POST_(HTTP)#Use_for_submitting_web_forms
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }

    private String getBookString(ArrayList<GuestModel> book) {

        Collections.reverse(book);
        String bookString = "";

        for(GuestModel guest: book){
            bookString += guest.toString();
        }

        return bookString;
    }
}
