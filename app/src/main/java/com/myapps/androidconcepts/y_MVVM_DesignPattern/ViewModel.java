package com.myapps.androidconcepts.y_MVVM_DesignPattern;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import org.jetbrains.annotations.NotNull;

public class ViewModel extends AndroidViewModel {       //weCanSayThisClassLike:providerClass, Observables or else viewModel class.
    private final Product_Model productModel;

    public ViewModel(@NonNull @NotNull Application application) {
        super(application);

        productModel = new Product_Model("Name: " + "Saifuddin", 9494641266L);
    }

    public Product_Model getProductModel() {

        return productModel;
    }
}