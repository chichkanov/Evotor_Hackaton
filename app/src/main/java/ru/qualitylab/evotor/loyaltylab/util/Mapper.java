package ru.qualitylab.evotor.loyaltylab.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import ru.evotor.framework.receipt.Position;
import ru.qualitylab.evotor.loyaltylab.model.ProductUi;

public class Mapper {

    public static Position convertProductToPostition(ProductUi product) {
        return Position.Builder.newInstance(
                "6e103da7-1a2c-4443-be9c-f2c73c1e72e4",
                "98235ad7-292e-489a-95c0-bc31b7119404",
                product.getName(),
                "шт",
                0,
                new BigDecimal(30),
                new BigDecimal(1)).build();
    }

    public static List<Position> convertProductToPostitionList(List<ProductUi> list) {
        List<Position> returnList = new ArrayList<>();
        for (ProductUi product : list) {
            returnList.add(Mapper.convertProductToPostition(product));
        }
        return returnList;
    }
}
