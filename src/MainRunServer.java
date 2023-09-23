import FileTransfer.FileTransferServer;

import java.util.Scanner;

public class MainRunServer {
    public static void main(String[] args){
        try {
            //start server
            FileTransferServer server = new FileTransferServer();
            System.out.println("Server launch");
            new Thread(() ->{
                try{
                    server.load();
                }catch (Exception exception){
                    System.out.println(exception);
                }
            }).start();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
