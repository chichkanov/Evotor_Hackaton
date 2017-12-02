package ru.qualitylab.evotor.loyaltylab.util;

import java.util.ArrayList;
import java.util.List;

import ru.evotor.framework.core.action.event.receipt.changes.position.IPositionChange;
import ru.evotor.framework.core.action.event.receipt.changes.position.PositionAdd;
import ru.evotor.framework.receipt.Position;

public class ChangesCreator {


    public static List<IPositionChange> createAddChangeList(List<Position> positionList){
        List<IPositionChange> positionAddList = new ArrayList<>();
        for(Position position : positionList){
            positionAddList.add(new PositionAdd(position));
        }
        return positionAddList;
    }

}
