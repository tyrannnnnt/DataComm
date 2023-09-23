import FileTransfer.FileTransferClient;

import java.util.Scanner;

public class MainRunClient {
    public static void main(String[] args){
        try {
            //start client
            String ipAddress = getIP();
            FileTransferClient.SERVER_IP = ipAddress;
            FileTransferClient client = new FileTransferClient();
            System.out.println("Client launch");
            new Thread(() ->{
                try{
                    client.start(ipAddress);
                }catch (Exception exception){
                    System.out.println(exception);
                }
            }).start();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    static String getIP(){
        System.out.println("Please input the server ip address: ");
        Scanner in = new Scanner(System.in);
        String str = in.nextLine();
        return str;
    }
}
