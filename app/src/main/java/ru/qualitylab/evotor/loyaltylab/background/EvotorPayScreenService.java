package ru.qualitylab.evotor.loyaltylab.background;

import android.content.Intent;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

import ru.evotor.framework.core.IntegrationService;
import ru.evotor.framework.core.action.event.receipt.discount.ReceiptDiscountEvent;
import ru.evotor.framework.core.action.event.receipt.discount.ReceiptDiscountEventProcessor;
import ru.evotor.framework.core.action.processor.ActionProcessor;
import ru.qualitylab.evotor.loyaltylab.ui.MainActivity;

public class EvotorPayScreenService extends IntegrationService {
    @Nullable
    @Override
    protected Map<String, ActionProcessor> createProcessors() {
        Map<String, ActionProcessor> processorMap = new HashMap<>();
        processorMap.put(ReceiptDiscountEvent.NAME_SELL_RECEIPT, new ReceiptDiscountEventProcessor() {
            @Override
            public void call(@NonNull String action, @NonNull ReceiptDiscountEvent event, @NonNull Callback callback) {
                try {
                    callback.startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        return processorMap;
    }
}