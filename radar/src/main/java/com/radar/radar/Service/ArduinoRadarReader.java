package com.radar.radar.Service;

import com.fazecast.jSerialComm.SerialPort;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ArduinoRadarReader {

    private SerialPort serialPort;
    private BufferedReader input;

    public ArduinoRadarReader(String portName) {
        System.out.println("1");
        serialPort = SerialPort.getCommPort(portName);
        serialPort.setBaudRate(9600);
        if (serialPort.openPort()) {
            System.out.println("Porta " + portName + " aberta com sucesso.");
            input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
        } else {
            throw new RuntimeException("Não foi possível abrir a porta " + portName);
        }
    }

    public RadarData readOnce() {
        try {
            if (input.ready()) {
                String line = input.readLine();
                System.out.println(line);
                if (line != null && line.contains(",")) {
                    String[] parts = line.trim().split(",");
                    int angle = Integer.parseInt(parts[0].trim());
                    int distance = Integer.parseInt(parts[1].trim());
                    return new RadarData(angle, distance);
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao ler dados do Arduino: " + e.getMessage());
        }
        return null;
    }

    public void close() {
        try {
            if (serialPort != null && serialPort.isOpen()) {
                serialPort.closePort();
                System.out.println("Porta serial fechada.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao fechar a porta serial: " + e.getMessage());
        }
    }

    // Classe auxiliar para armazenar os dados
    public static class RadarData {
        public final int angle;
        public final int distance;

        public RadarData(int angle, int distance) {
            this.angle = angle;
            this.distance = distance;
        }

        @Override
        public String toString() {
            return "Ângulo: " + angle + "°, Distância: " + distance + "cm";
        }
    }
}
