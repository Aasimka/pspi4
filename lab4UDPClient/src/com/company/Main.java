package com.company;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {//метод main
        try {
            UDPClient client = new UDPClient();//создание объекта client
            client.runClient();//вызов метода объекта client
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }

}
