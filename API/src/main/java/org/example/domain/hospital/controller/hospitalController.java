package org.example.domain.hospital.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/hospital")
@RequiredArgsConstructor
public class hospitalController {

    @PostMapping("/application")
    public void hospitalApplication(){

    }

    @PutMapping("/update")
    public void hospitalUpdate(){

    }

    @DeleteMapping("/resign")
    public void hospitalResign(){

    }

    @GetMapping("/list")
    public ResponseEntity<? extends Object> GethospitalList(){
        return null;
    }

    @GetMapping("/info")
    public ResponseEntity<? extends Object> GethospitalInfo(){
        return null;
    }




}
