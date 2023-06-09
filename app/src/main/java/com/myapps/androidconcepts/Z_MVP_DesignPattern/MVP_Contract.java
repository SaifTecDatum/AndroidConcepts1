package com.myapps.androidconcepts.Z_MVP_DesignPattern;

public interface MVP_Contract {

    interface View {
        void onSuccess(String message);

        void onFailure(String message);
    }

    interface Presenter {
        void doLogin(String email, String password);
    }

}
