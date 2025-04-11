package com.radar.radar.Service;


import com.fazecast.jSerialComm.SerialPort;

import java.util.Scanner;

public class Radarcom {

    public static void main(String[] args) {
         SerialPort[] ports = SerialPort.getCommPorts();

        if (ports.length == 0) {
            System.out.println("Nenhuma porta serial encontrada.");
            return;
        }

        // Escolha a primeira porta disponível (ou especifique manualmente)
        SerialPort arduinoPort = ports[0];
        arduinoPort.setBaudRate(9600);

        if (arduinoPort.openPort()) {
            System.out.println("Conectado à porta: " + arduinoPort.getSystemPortName());
        } else {
            System.out.println("Falha ao conectar à porta.");
            return;
        }

        Scanner scanner = new Scanner(arduinoPort.getInputStream());

        while (true) {
            if (scanner.hasNextLine()) {
                String linha = scanner.nextLine().trim();
                try {
                    String[] partes = linha.split(",");
                    int grau = Integer.parseInt(partes[0]);
                    double distancia = Double.parseDouble(partes[1]);

                    System.out.println("Grau: " + grau + "°, Distância: " + distancia + " cm");
                } catch (Exception e) {
                    System.out.println("Formato inválido: " + linha);
                }
            }
        }
    }
}
