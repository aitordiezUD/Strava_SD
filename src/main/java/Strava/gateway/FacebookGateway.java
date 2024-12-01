package Strava.gateway;

import java.io.*;
import java.net.Socket;
import java.util.Properties;

public class FacebookGateway implements IAuthGateway{

    private static final String CONFIG_FILE = "src/main/java/Strava/config.properties";
//    private static final int PORT = 8082;
//    private static final String SERVER_ADDRESS = "localhost";
    private static final String DELIMITER = "#";
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private static int PORT;
    private static String SERVER_ADDRESS;

    static {
        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            Properties prop = new Properties();
            prop.load(input);
            SERVER_ADDRESS = prop.getProperty("facebook.address");
            PORT = Integer.parseInt(prop.getProperty("facebook.port"));
        } catch (IOException ex) {
            System.err.println("# FacebookGateway - Error loading configuration: " + ex.getMessage());
            SERVER_ADDRESS = "localhost"; // Defaults
            PORT = 8082;
        }
    }

    public FacebookGateway() {
    }

    @Override
    public boolean checkUserExists(String email) {
        String response = null;

        connect();
        sendMessage("check" + DELIMITER + email);
        return processResponse();

    }

    @Override
    public boolean userAuth(String email, String password) {
        String response = null;

        connect();
        sendMessage("auth" + DELIMITER + email + DELIMITER + password);
        return processResponse();
    }

    private boolean processResponse() {
        String response;
        response = receiveMessage();
        switch (response){
            case "OK":
                disconnect();
                return true;
            case "NO":
                disconnect();
                return false;
            default:
                disconnect();
                return false;
        }
    }


    public void connect() {
        try {
            this.socket = new Socket(SERVER_ADDRESS, PORT);
            this.out = new DataOutputStream(socket.getOutputStream());
            this.in = new DataInputStream(socket.getInputStream());
        } catch (Exception e) {
            System.err.println("# FacebookGateway - TCPConnection IO error:" + e.getMessage());
        }
    }

    public void sendMessage(String message){
        try {
            out.writeUTF(message);
            out.flush();
        } catch (IOException e) {
            System.err.println("# FacebookGateway - TCPConnection IO error:" + e.getMessage());
        }
    }

    public String receiveMessage(){
        String data = null;
        try {
            data = in.readUTF();
//            System.out.println("Received data: " + data);
        } catch (IOException e) {
            System.err.println("# FacebookGateway - TCPConnection IO error:" + e.getMessage());
        }
        return data;
    }

    public void disconnect(){
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.err.println("# FacebookGateway - TCPConnection IO error:" + e.getMessage());
        }
    }

}
