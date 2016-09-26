package com.swiftpot.swiftalertmain.models;

/**
 * @author Ace Programmer Rbk
 *         <Rodney Kwabena Boachie at [rodney@swiftpot.com,rbk.unlimited@gmail.com]> on
 *         26-Sep-16 @ 9:30 PM
 */
public class SignInResponse {

    String userId;

    String userName;

    int creditBalance;

    String firstName;

    String lastName;

    String token;

    public SignInResponse() {
    }

    public SignInResponse(String userId, String userName, int creditBalance, String firstName, String lastName) {
        this.userId = userId;
        this.userName = userName;
        this.creditBalance = creditBalance;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public SignInResponse(String userId, String userName, int creditBalance) {
        this.userId = userId;
        this.userName = userName;
        this.creditBalance = creditBalance;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getCreditBalance() {
        return creditBalance;
    }

    public void setCreditBalance(int creditBalance) {
        this.creditBalance = creditBalance;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
