package com.PIS2022L.kafkaproducerapp.Controllers;

import com.PIS2022L.kafkaproducerapp.Models.Test.TestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/test/")
public class TestController {
    @GetMapping()
    public ResponseEntity<TestResponse> TestResponse() {
        return new ResponseEntity<>(new TestResponse(), HttpStatus.OK);
    }
}
