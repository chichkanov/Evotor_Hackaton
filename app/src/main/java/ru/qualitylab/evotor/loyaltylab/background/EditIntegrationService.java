package ru.qualitylab.evotor.loyaltylab.background;

import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

import ru.evotor.framework.core.IntegrationService;
import ru.evotor.framework.core.action.event.receipt.before_positions_edited.BeforePositionsEditedEvent;
import ru.evotor.framework.core.action.event.receipt.before_positions_edited.BeforePositionsEditedEventProcessor;
import ru.evotor.framework.core.action.processor.ActionProcessor;
import ru.qualitylab.evotor.loyaltylab.util.Logger;

public class EditIntegrationService extends IntegrationService {
    @Nullable
    @Override
    protected Map<String, ActionProcessor> createProcessors() {
        Map<String, ActionProcessor> map = new HashMap<>();
        map.put(BeforePositionsEditedEvent.NAME_SELL_RECEIPT, new BeforePositionsEditedEventProcessor() {
            @Override
            public void call(@NonNull String action, @NonNull BeforePositionsEditedEvent
                    event, @NonNull Callback callback) {
                Logger.log("Kek");
                try {
                    callback.onResult(event);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        return map;
    }
}
