package com.example.appsale07062022.data.remote.dto;

public class AppResponse<T> {
    public T data;
    public String message;
    public Status status;

    public AppResponse(T data, Status status) {
        this.data = data;
        this.status = status;
    }

    public AppResponse(String message, Status status) {
        this.message = message;
        this.status = status;
    }

    public static class Success<T> extends AppResponse<T> {
        public Success(T data) {
            super(data, Status.SUCCESS);
        }
    }

    public static class Error<T> extends AppResponse<T> {
        public Error(String message) {
            super(message, Status.ERROR);
        }
    }

    public static class Loading<T> extends AppResponse<T> {
        public Loading(T data) {
            super(data, Status.LOADING);
        }
    }

    public enum Status {
        SUCCESS, LOADING, ERROR
    }
}
