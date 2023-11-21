package com.company;
import java.io.*;
import java.net.*;

public class Main {

    public static void main(String[] args) {
	// write your code here
        try{
            System.out.println("Server connection...");
            Socket clientSocket = new Socket("127.0.0.1", 2525);
            System.out.println("Connection is good");

            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
            ObjectOutputStream coos = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream cois = new ObjectInputStream(clientSocket.getInputStream());

            System.out.println("Введите размерность матрицы с разделителем '*'\n\t(quite - выход)");
            String clientMessage = stdin.readLine();
            System.out.println("Вы ввели: " + clientMessage);
            while (!clientMessage.equals("quite"))
            {
                coos.writeObject(clientMessage);
                System.out.println("~Server~: \n" + cois.readObject());
                System.out.println("---------------------------");

                clientMessage = stdin.readLine();
                System.out.println("Вы ввели: " + clientMessage);
            }
            coos.close();
            cois.close();
            clientSocket.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
