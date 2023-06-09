package com.myapps.androidconcepts.y_MVVM_DesignPattern.viewModels;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.myapps.androidconcepts.y_MVVM_DesignPattern.models.NicePlaces;
import com.myapps.androidconcepts.y_MVVM_DesignPattern.repositories.NicePlacesRepository;

import java.util.List;

public class MvvmOriginalViewModel extends ViewModel {
    private static MutableLiveData<List<NicePlaces>> mutableLiveDataList;
    private NicePlacesRepository mRepo;
    private static MutableLiveData<Boolean> mIsUpdating = new MutableLiveData<>();

    public void inIt() {
        if (mutableLiveDataList != null) {
            return;
        }
        mRepo = NicePlacesRepository.getInstance();
        mutableLiveDataList = mRepo.getMutableDataList();
    }


    public static void addNewData(final NicePlaces nicePlaces) {
        mIsUpdating.setValue(true);

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Thread.sleep(2000);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);

                List<NicePlaces> currentPlacesList = mutableLiveDataList.getValue();
                currentPlacesList.add(nicePlaces);
                mutableLiveDataList.postValue(currentPlacesList);
                mIsUpdating.postValue(false);
            }
        }.execute();

    }


    public LiveData<List<NicePlaces>> getLiveData() {

        return mutableLiveDataList;
    }

    public LiveData<Boolean> getIsUpdating() {

        return mIsUpdating;
    }

}
