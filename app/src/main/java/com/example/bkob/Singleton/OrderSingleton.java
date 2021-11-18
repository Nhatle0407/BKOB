package com.example.bkob.Singleton;

import com.example.bkob.models.BookModel;
import com.example.bkob.models.NotifyModel;

public class OrderSingleton {
    private  static NotifyModel notifyModel = null;

    public static NotifyModel getNotifyModel() {
        return notifyModel;
    }

    public static void setNotifyModel(NotifyModel notifyModel) {
        OrderSingleton.notifyModel = notifyModel;
    }
}
