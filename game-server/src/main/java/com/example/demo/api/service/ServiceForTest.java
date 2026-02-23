package com.example.demo.api.service;

import org.springframework.stereotype.Service;

@Service
public class ServiceForTest {
    int first;
    int second;

    public int plus(int first, int second) {
        return first + second;
    }

}
