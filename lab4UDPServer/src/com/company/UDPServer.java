package com.company;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer {

    public final static int DEFAULT_PORT = 8001;
    public final String QUIT_CMD = "QUIT";

    public void runServer() throws IOException {//метод сервера runServer
        DatagramSocket s = null;//создание объекта DatagramSocket
        try {
            boolean stopFlag = false;//создание флага stopFlag и его инициализация значением false
            byte[] buf = new byte[512];//буфер для приема/передачи дейтаграммы
            s = new DatagramSocket(DEFAULT_PORT);//привязка сокета к реальному объекту с портом DEFAULT_PORT
            String num = "", cmd = "";
            System.out.println("UDPServer: Started on " + s.getLocalAddress() + ":"
                    + s.getLocalPort());//вывод к консоль сообщения

            while(!stopFlag) {//цикл до тех пор, пока флаг не примет значение true
                DatagramPacket recvPacket = new DatagramPacket(buf, buf.length);//создание объекта дейтаграммы для получения данных
                s.receive(recvPacket);//помещение полученного содержимого в объект дейтаграммы
                num = new String(recvPacket.getData()).trim();//извлечение команды из пакет
                System.out.println("UDPServer: " + num);
                Double x = Double.parseDouble(num.split("\s")[0]),
                        y = Double.parseDouble(num.split("\s")[1]),
                        z = Double.parseDouble(num.split("\s")[2]), rez;
                System.out.println(x + " " +  y + " " + z);

                rez = Math.log10(Math.pow(Math.exp(x-y)+Math.pow(x,Math.abs(y))+z,0.5))*(x-Math.pow(x,3)/3-Math.pow(x,7)/7);
                //rez = Math.log(Math.pow(y, -Math.sqrt(Math.abs(x)))) * (x - y / 2.0) + Math.pow(Math.sin(Math.atan(z)), 2) + Math.exp(x + y);
                System.out.println(rez);


                buf = rez.toString().getBytes();

                DatagramPacket sendPacket = new DatagramPacket(buf,
                        buf.length, recvPacket.getAddress(), recvPacket.getPort());//формирование объекта дейтаграммы для отсылки данных
                s.send(sendPacket);//послать сами данные


                s.receive(recvPacket);
                cmd = new String(recvPacket.getData()).trim();
                if (cmd == QUIT_CMD)//Завершение работы сервера
                {
                    stopFlag = true;
                }

            } // while(server is not stopped)
            System.out.println("UDPServer: Stopped");
        }
        finally {
            if (s != null) {
                s.close();//закрытие сокета сервера
            }
        }
    }

}
