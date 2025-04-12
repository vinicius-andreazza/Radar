package com.radar.radar.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.radar.radar.Service.ArduinoRadarReader;
import com.radar.radar.Service.ArduinoRadarReader.RadarData;



@Controller
public class RadarController {

    
 
   
    @GetMapping("/")
    public String radarFront() {
        
        return "index";
    }



}
