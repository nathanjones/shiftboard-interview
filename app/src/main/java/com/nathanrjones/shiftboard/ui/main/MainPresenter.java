package com.nathanrjones.shiftboard.ui.main;

import android.support.annotation.Nullable;

import com.nathanrjones.shiftboard.data.repository.PersonRepository;

public class MainPresenter implements MainScreen.Presenter {

    private final PersonRepository personRepo;

    MainPresenter(PersonRepository personRepo) {
        this.personRepo = personRepo;
    }

    @Override
    public void attachView(MainScreen.View view) {

    }

    @Override
    public void detachView() {

    }

    @Override
    public void initialize(@Nullable String personId) {

    }
}
