package com.myapps.androidconcepts.Z_MVP_DesignPattern;

public class MVP_Presenter implements MVP_Contract.Presenter {
    MVP_Contract.View view;

    public MVP_Presenter(MVP_Contract.View view) {
        this.view = view;
    }

    @Override
    public void doLogin(String email, String password) {
        if (email.equals("saifmsu15@gmail.com") && password.equals("dummy1")) {
            view.onSuccess("Login Successful");
        } else {
            view.onFailure("Wrong Password or Email..!");
        }
    }

}
