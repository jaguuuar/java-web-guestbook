import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Form implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String response = "";
        String method = httpExchange.getRequestMethod();


        // Send a form if it wasn't submitted yet.
        if(method.equals("GET")){
            response = "<html><body>" +
                    "<h1>Simple Guestbook</h1>" +
                    "<form method=\"POST\">\n" +
                    "  Message<br>\n" +
                    "  <input type=\"text\" name=\"message\" value=\"Msg\">\n" +
                    "  <br><br>\n" +
                    "  Name<br>\n" +
                    "  <input type=\"text\" name=\"name\" value=\"Cris\">\n" +
                    "  <br><br>\n" +
                    "  <input type=\"submit\" value=\"Submit\">\n" +
                    "</form> " +
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

            response = "<html><body>" +
                    "<h1>Simple Guestbook</h1>" +
                    "<p style=\"background-color:powderblue\"><p1><b>" + inputs.get("message") + "</b></p1><br>" +
                    "<p2>Name: </p2>" + "<p3><b>" + inputs.get("name") + "</b></p3><br>" +
                    "<p4>Date: </p4>" + "<p5>" + dateFormat.format(date) + "<p/5></p>" +
                    "</body><html>";
        }

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    /**
     * Form data is sent as a urlencoded string. Thus we have to parse this string to get data that we want.
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
}
