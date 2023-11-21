package com.company;
import java.io.*;

public class Main
{
    public static void main(String[] args) {//метод main
        try {
            UDPServer udpSvr = new UDPServer();//создание объекта udpSvr
            udpSvr.runServer();//вызов метода объекта runServer
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}


