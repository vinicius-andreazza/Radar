package com.radar.radar.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.radar.radar.Service.ArduinoRadarReader;
import com.radar.radar.Service.ArduinoRadarReader.RadarData;

@RestController
public class RadarRestController {

    private static int pos = 0;
    private static Boolean flag = true;
    //private final ArduinoRadarReader radarReader = new ArduinoRadarReader("COM4");
/* 
    @GetMapping("/medidas")
    public RadarData getRadarData() {
        ArduinoRadarReader.RadarData data = radarReader.readOnce();
        if (data != null) {
            return data;
        } else {
            return new ArduinoRadarReader.RadarData(0, 0); // valor default se nada for

        }
    }*/
    
         @GetMapping("/medidas")
         public Map<String, Integer> getMockRadar() {
         Map<String, Integer> data = new HashMap<>();
         data.put("angle", pos);

     
         if(flag){
            pos+=1;
            if(pos==180){
                flag = false;
            }
            
         } else{
            pos-=1;
            if(pos==0){
                flag = true;
            }
         }
         data.put("distance", new Random().nextInt(360));
         return data;
         }

    
}
