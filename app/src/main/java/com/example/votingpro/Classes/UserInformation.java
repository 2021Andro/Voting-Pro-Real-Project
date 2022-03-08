package com.example.votingpro.Classes;

import java.io.Serializable;

public class UserInformation implements Serializable {

    private String UserId;
    private String ProfileImage;
    private String Name;
    private String Email;
    private String birthDay;
    private String PhoneNumber;
    private String PinCode;

    public UserInformation() {
    }

    public UserInformation(String userId, String profileImage, String name, String email, String birthDay, String phoneNumber, String pinCode) {
        this.UserId = userId;
        this.ProfileImage = profileImage;
        this.Name = name;
        this.Email = email;
        this.birthDay = birthDay;
        this.PhoneNumber = phoneNumber;
        this.PinCode = pinCode;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getProfileImage() {
        return ProfileImage;
    }

    public void setProfileImage(String profileImage) {
        ProfileImage = profileImage;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getPinCode() {
        return PinCode;
    }

    public void setPinCode(String pinCode) {
        PinCode = pinCode;
    }

    @Override
    public String toString() {
        return "UserInformation{" +
                "UserId='" + UserId + '\'' +
                ", ProfileImage='" + ProfileImage + '\'' +
                ", Name='" + Name + '\'' +
                ", Email='" + Email + '\'' +
                ", birthDay='" + birthDay + '\'' +
                ", PhoneNumber='" + PhoneNumber + '\'' +
                ", PinCode='" + PinCode + '\'' +
                '}';
    }
}
