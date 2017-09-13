package com.healthcamp.healthapp.models.HomeCategory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ITH-143 on 01-Sep-17.
 */

public class MainResult  {
    public MainResult() {

    }

    @SerializedName("results")
    @Expose
    private Results result;

    public Results getResult() {
        return result;
    }

    public void setResult(Results result) {
        this.result = result;
    }
}
