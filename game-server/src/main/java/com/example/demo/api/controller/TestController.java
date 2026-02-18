package com.example.demo.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.api.dto.TestDto;
import com.example.demo.api.dto.TestResponseDto;
import com.example.demo.api.service.ServiceForTest;

@RestController
public class TestController {
    private final ServiceForTest serviceForTest;

    public TestController(ServiceForTest serviceForTest) {
        this.serviceForTest = serviceForTest;
    }

    @PostMapping("/sum")
    public TestResponseDto sum(@RequestBody TestDto dto) {
        int result = serviceForTest.plus(dto.first(), dto.second());
        return new TestResponseDto(result);
    }
}
