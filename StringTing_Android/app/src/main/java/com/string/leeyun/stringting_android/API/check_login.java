package com.string.leeyun.stringting_android.API;

/**
 * Created by leeyun on 2017. 12. 6..
 */

public class check_login {

    private String result;
    private String status;
    private String id;
    private String access_token;
    private String sex;
    private String email;
    private String password;
    private String fcm_token;
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getFcm_token() {
        return fcm_token;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public void setFcm_token(String fcm_token) {
        this.fcm_token = fcm_token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public String getPassword(String s) {

        return password;
    }

    public String getEmail() {

        return email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setToken(String token) {
        this.access_token = token;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getId() {

        return id;
    }

    public String getToken() {
        return access_token;
    }

    public String getSex() {
        return sex;
    }

    public String getResult() {
        return result;
    }

    public String getStatus() {
        return status;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
