package org.example.domain.hospital.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hospital")
@RequiredArgsConstructor
public class hospitalController {


    @GetMapping("/get_list")
    public ResponseEntity<? extends Object> GethospitalList(){
        return null;
    }
}
