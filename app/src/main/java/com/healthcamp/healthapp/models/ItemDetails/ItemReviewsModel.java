package com.healthcamp.healthapp.models.ItemDetails;

/**
 * Created by Kishor Bikram Oli on 28-Aug-17.
 */

public class ItemReviewsModel {
    public String UserName;
    public String Description;
    public String DateTime;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }
}
