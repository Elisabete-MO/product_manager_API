package com.exercise.API_Rest.controllers.dto;

public record ResponseDTO<T>(String message, T data) {
}
