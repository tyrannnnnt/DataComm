package FileTransfer;

import javafx.concurrent.Task;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class FileTransferServer extends ServerSocket{
    private static final int SERVER_PORT = 8899; // port number

    final int stringIdentifier = 2222;

    public FileTransferServer() throws Exception {
        super(SERVER_PORT);
    }

    /**
     * handle every file from client use thread
     * @throws Exception
     */
    public void load() throws Exception {
        while (true) {
            Socket socket = this.accept();
            /**
             * asynchronous
             */
            // use new thread handle task
            new Thread(new Task(socket)).start();
        }
    }

    /**
     * handle file transfer
     */
    class Task implements Runnable {
        private Socket socket;

        private DataInputStream dis;    //data input stream
        private DataOutputStream dos;   //data output stream

        public Task(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            new Thread(() -> {
                while (true) {
                    try {
                        receiveMsg(this.socket);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }).start();

            new Thread(() -> {
                while (true) {
                    try {
                        sendStr(getInputStr(), stringIdentifier);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }).start();
        }

        /**
         * receive and handle message received from clinet
         *
         * @param client socket of the server
         */
        private void receiveMsg(Socket client) {
            try {
                //initial data input stream of client
                dis = new DataInputStream(client.getInputStream());
                while (true) {
                    //read msg length
                    int len = dis.readInt();
                    //read msg identifier
                    int specifier = dis.readInt();
                    switch (specifier) {
                        //receive string from clint
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
         * send string to client
         *
         * @param str        message
         * @param identifier identifier number
         */
        public void sendStr(String str, int identifier) {
            try {
                dos = new DataOutputStream(socket.getOutputStream());
                dos.writeInt(str.length());
                dos.writeInt(identifier);
                dos.writeUTF(str);
                dos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private String getInputStr() {
            System.out.println("Please input the msg to send: ");
            Scanner in = new Scanner(System.in);
            String str = in.nextLine();
            if (!str.equals("exit")) {
                return str;
            }
            System.exit(0);
            return "";
        }
    }
}
