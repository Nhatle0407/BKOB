package com.example.bkob.Singleton;

import com.example.bkob.models.ReceiveModel;
import com.example.bkob.views.adapters.ReceiveAdapter;

public class DetailSaleSingleton {
    private static ReceiveModel receiveModel =  null;
    public static ReceiveModel getReceiveModel(){
        return  receiveModel;
    }
    public static void setReceiveModel(ReceiveModel receiveModel1){
        receiveModel =receiveModel1;
    }

}
