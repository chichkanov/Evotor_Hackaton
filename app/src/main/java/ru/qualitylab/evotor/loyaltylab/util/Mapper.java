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
}
