package com.company;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {//описание класса клиента
    public void runClient() throws IOException {//метод клиента runClient
        DatagramSocket s = null;//создание дейтаграммы
        InputStream inputStream = System.in;
        Reader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        try {
            System.out.println("UDPClient: Started");
            String quit = "", nums = "";
            Integer[] numbers = new Integer[3];
            byte[] bufOutput = new byte[512]; //буфер для приема/передачи дейтаграммы
            byte[] bufInput = new byte[512]; //буфер для приема/передачи дейтаграммы
            s = new DatagramSocket();//привязка сокета к реальному объету
            while (quit != "QUIT") {
                System.out.println("Введите значения: x, y, z");
                for (int i = 0; i < 3;i ++) {
                    numbers[i] = Integer.parseInt(bufferedReader.readLine());
                    nums += numbers[i].toString() + " ";
                }
                bufInput = nums.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(bufInput, bufInput.length, InetAddress.getByName("127.0.0.1"), 8001);//создание дейтаграммы для отсылки данных
                s.send(sendPacket);//посылка дейтаграммы

                DatagramPacket recvPacket = new DatagramPacket(bufOutput, bufOutput.length);//создание дейтаграммы для получения данных
                s.receive(recvPacket);//получение дейтаграммы

                String rez = new String(recvPacket.getData()).trim();//извлечение данных (версии сервера)
                System.out.println(rez);

                System.out.println("Нажмите Enter для продолжения.\n\tQUIT - Выход");
                quit = bufferedReader.readLine();
                bufInput = "".getBytes();
                if (quit == "QUIT") {
                    bufInput = "QUIT".getBytes();
                }
                sendPacket.setData(bufInput);//установить массив посылаемых данных
                sendPacket.setLength(bufInput.length);//установить длину посылаемых данных
                s.send(sendPacket); //послать данные серверу
                nums = "";
            }
            System.out.println("UDPClient: Ended");
        }
        finally {
            if (s != null) {
                s.close();//закрытие сокета клиента
            }  }  }

}
