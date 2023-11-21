package com.company;
import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Random;


public class Main {

    public static void main(String[] args) {
        // write your code here
        ServerSocket serverSocket = null;
        Socket clientAccepted = null;

        ObjectInputStream sois = null;
        ObjectOutputStream soos = null;

        try {
            System.out.println("Sever starting...");
            try
            {
                serverSocket = new ServerSocket(2525);
            } catch (IOException e)
            {
                throw new RuntimeException(e);
            }
            try
            {
                clientAccepted = serverSocket.accept();
            } catch (IOException e)
            {
                throw new RuntimeException(e);
            }

            System.out.println("Connection is good");

            sois = new ObjectInputStream(clientAccepted.getInputStream());
            soos = new ObjectOutputStream(clientAccepted.getOutputStream());

            String clientMessageRecieved = (String) sois.readObject();

            while (!clientMessageRecieved.equals("quiet"))
            {
                System.out.println("message recieved: " + clientMessageRecieved);

                //реализация задания
                String[] arr = clientMessageRecieved.replaceAll("\s", "").split("[*]");
                System.out.println(arr[0] + ";" + arr[1]);
                Integer[] numbers = new Integer[2];

                int i = 0;
                if (clientMessageRecieved.replaceAll("\s", "").split("[*]").length == 2 &&
                        isExist(clientMessageRecieved))
                {
                    for (String it : arr)
                    {
                        numbers[i] = Integer.parseInt(it);
                        i++;
                    }
                    Random Rand = new Random();
                    int rows = numbers[0];
                    int cols = numbers[1];
                    int[][] matrix = new int[rows][cols];
                    for (int n = 0; n < rows; n++)
                    {
                        for (int m = 0; m < cols; m++)
                        {
                            matrix[n][m] = Rand.nextInt(50);
                        }
                    }
                    clientMessageRecieved = "";
                    for (int n=0; n< rows;n++)
                    {
                        for (int m=0; m<cols;m++)
                        {
                            clientMessageRecieved+=String.valueOf(matrix[n][m])+" ";
                        }
                        clientMessageRecieved += "\n";
                    }
                    int minRows=0;
                    int minValue=matrix[0][0];
                    for (int p=0;p<rows;p++)
                    {
                        for(int o=0;o<cols;o++)
                        {
                            if(minValue>matrix[p][o])
                            {
                                minValue=matrix[p][o];
                                minRows=p;
                            }
                        }
                    }
                    clientMessageRecieved+="Индекс строки с минимальным элементом: "+minRows+"\n";
                    int maxCols=0;
                    int maxValue=matrix[0][0];
                    for (int p=0;p<rows;p++)
                    {
                        for(int o=0;o<cols;o++)
                        {
                            if(maxValue<matrix[p][o])
                            {
                                maxValue=matrix[p][o];
                                maxCols=o;
                            }
                        }
                    }
                    clientMessageRecieved+="Индекс столбца с максимальным элементом: "+ maxCols+"\n";
                } else
                {
                    clientMessageRecieved = "Неверный формат ввода.\n ";
                    soos.writeObject(clientMessageRecieved);
                    clientMessageRecieved = (String) sois.readObject();
                }
                soos.writeObject(clientMessageRecieved);
                clientMessageRecieved = (String) sois.readObject();
            }

        }
        catch (Exception e){ }
        finally {
            try {
                sois.close();
                soos.close();
                clientAccepted.close();
                serverSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    } public static boolean isExist(String str)
    {
        boolean isExist = true;
        String[] arr = str.replaceAll("\s", "").split("[*]");
        for (String it : arr)
        {
            try
            {
                Integer.parseInt(it);
            }
            catch (NumberFormatException e)
            {
                isExist = false;
                break;
            }
        }
        return isExist;
    }
}