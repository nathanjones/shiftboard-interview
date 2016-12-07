package com.nathanrjones.shiftboard.ui.base;

import android.os.Bundle;

public interface MvpPresenter<V extends MvpView> {

    void attachView(V view);

    void detachView();

}
