package com.example.demo.api.errors;

import java.util.List;

public record ApiError(int status, String message, List<ApiFieldError> errors) {}
