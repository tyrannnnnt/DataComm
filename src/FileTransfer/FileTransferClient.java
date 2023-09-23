package FileTransfer;

import java.io.*;
import java.net.Socket;

public class FileTransferClient extends Socket {
    public static String SERVER_IP = ""; // server IP
    private static final int SERVER_PORT = 8899; // port number

    final int stringIdentifier = 2222;

    private Socket client;          //client socket
    private DataOutputStream dos;   //client data output stream
    private DataInputStream dis;    //client data input stream

    private boolean exit = false;

    /**
     * connect to server
     * @throws Exception
     */
    public FileTransferClient() throws Exception {
        super(SERVER_IP, SERVER_PORT);
        this.client = this;
        System.out.println("Client[port:" + client.getLocalPort() + "] connect to server successful!");
    }

    public void quitSys(){
        this.exit = true;
    }

    /**
     * receive and handle message received from server
     * @param client    socket of the client
     */
    private void receiveMsg(Socket client){
        try{
            //initial data input stream of client
            dis = new DataInputStream(client.getInputStream());
            while (true){
                //read msg length
                int len = dis.readInt();
                //read msg identifier
                int specifier = dis.readInt();
                switch (specifier){
                    //receive B from server
                    case stringIdentifier:
                        String str = "";
                        String temp;
                        while (!(temp = dis.readUTF()).equalsIgnoreCase("")) {
                            str += temp;
                            if (temp.isEmpty()) {
                                break;
                            }
                        }
                        System.out.println(str);
                        //todo: use received data do sth
                        break;
                    default:
                        System.out.println("Unknown identifier!");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * send string with identifier to server
     * @param str   the message need to be sent
     * @param identifier    the message identifier
     */
    private void sendStr(String str, int identifier){
        try {
            dos.writeInt(str.length());
            dos.writeInt(identifier);
            dos.writeUTF(str);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * initial transfer client
     */
    public void start(String IP){
        try{
            SERVER_IP = IP;
            while (!exit) {
                receiveMsg(client);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
