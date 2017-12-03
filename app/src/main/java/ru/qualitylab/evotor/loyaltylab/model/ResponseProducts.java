package ru.qualitylab.evotor.loyaltylab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseProducts {

    @SerializedName("predictions")
    @Expose
    private List<ProductUi> list = null;

    public List<ProductUi> getProducts() {
        return list;
    }

}

