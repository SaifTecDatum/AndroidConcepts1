package com.myapps.androidconcepts.y_MVVM_DesignPattern.repositories;

import androidx.lifecycle.MutableLiveData;

import com.myapps.androidconcepts.y_MVVM_DesignPattern.models.NicePlaces;

import java.util.ArrayList;
import java.util.List;

public class NicePlacesRepository {
    private static NicePlacesRepository instance;
    private List<NicePlaces> nicePlacesList = new ArrayList<>();
    
    public static NicePlacesRepository getInstance() {
        if (instance == null) {
            instance = new NicePlacesRepository();
        }
        return instance;
    }
    
    public MutableLiveData<List<NicePlaces>> getMutableDataList() {
        setPlaces();
        
        MutableLiveData<List<NicePlaces>> data = new MutableLiveData<>();
        
        data.setValue(nicePlacesList);
        
        return data;
    }
    
    private void setPlaces() {
        nicePlacesList.add(new NicePlaces("https://i.redd.it/tpsnoz5bzo501.jpg",
                "Trondheim"));
        nicePlacesList.add(new NicePlaces("https://i.redd.it/qn7f9oqu7o501.jpg",
                "Portugal"));
        nicePlacesList.add(new NicePlaces("https://i.redd.it/j6myfqglup501.jpg",
                "Rocky Mountain National Park"));
        nicePlacesList.add(new NicePlaces("https://i.redd.it/0h2gm1ix6p501.jpg",
                "Havasu Falls"));
        nicePlacesList.add(new NicePlaces("https://i.redd.it/k98uzl68eh501.jpg",
                "Mahahual"));
        nicePlacesList.add(new NicePlaces("https://i.redd.it/obx4zydshg601.jpg",
                "Austrailia"));
        nicePlacesList.add(new NicePlaces("https://media.cntraveler.com/photos/" +
                "60595e75c685cfec854722a8/master/w_2580%2Cc_limit/Azores-GettyImages-1183442594.jpg",
                "The Azores, Portugal"));
        nicePlacesList.add(new NicePlaces("https://media.cntraveler.com/photos/" +
                "5cb63a087b743b350a60a8d9/master/w_2580%2Cc_limit/Antarctica-_GettyImages-148815908.jpg",
                "Antarctica"));
    }
    
}
