package com.nathanrjones.shiftboard.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

public interface MvpPresenter<V extends MvpView> {

    void attachView(V view);

    void detachView();

    @Nullable
    V getView();

}
