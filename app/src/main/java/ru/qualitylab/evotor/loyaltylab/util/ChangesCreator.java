package ru.qualitylab.evotor.loyaltylab.util;

import java.util.ArrayList;
import java.util.List;

import ru.evotor.framework.core.action.event.receipt.changes.position.PositionAdd;
import ru.evotor.framework.receipt.Position;

public class ChangesCreator {


    public static List<PositionAdd> createAddChangeList(List<Position> positionList){
        List<PositionAdd> positionAddList = new ArrayList<>();
        for(Position position : positionList){
            positionAddList.add(new PositionAdd(position));
        }
        return positionAddList;
    }

}
