package ru.qualitylab.evotor.loyaltylab.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ru.evotor.framework.receipt.Position;
import ru.qualitylab.evotor.loyaltylab.model.ProductUi;

public class Mapper {

    public static Position convertProductToPostition(ProductUi product) {
        return Position.Builder.newInstance(
                UUID.randomUUID().toString(),
                null,
                product.getName(),
                "шт",
                0,
                new BigDecimal(product.getPrice()),
                new BigDecimal(1)).build();
    }

    public static List<Position> convertProductToPostitionList(List<ProductUi> list) {
        List<Position> returnList = new ArrayList<>();
        for (ProductUi product : list) {
            returnList.add(Mapper.convertProductToPostition(product));
        }
        return returnList;
    }

    public static List<String> createUidBodyFromReceipt(List<Position> list) {
        List<String> arr = new ArrayList<>();
        for (Position position : list) {
            if (position.getProductCode() != null) {
                arr.add(getUidFromBarcode(position.getProductCode()));
            }
        }
        Logger.log(arr.toString());
        return arr;
    }

    private static String getUidFromBarcode(String code) {
        switch (code) {
            case "1":
                return "1171600";
            case "3":
                return "3780640";
            case "4":
                return "1950869";
            case "5":
                return "9685";
            default:
                return "1";
        }
    }
}
