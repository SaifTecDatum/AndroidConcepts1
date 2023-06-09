package com.myapps.androidconcepts.Helpers;

public class Sample_SingleTon {     //justToCheckResultInLogcat.
    private static Sample_SingleTon sample_singleTon = null;
    public int ABC = 0;

    private Sample_SingleTon() {    //InSingletonWeRestrictConstructorAsPrivate, byDefaultWeHavePublicAccess.

    }

    public static Sample_SingleTon getInstance() {      //hereInMethodWeAreGivingAccessToUseInAnotherClassByKeepingPublic.
        if (sample_singleTon == null) {
            sample_singleTon = new Sample_SingleTon();
        }
        return sample_singleTon;
    }
}