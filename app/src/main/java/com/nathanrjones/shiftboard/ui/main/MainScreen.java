package com.nathanrjones.shiftboard.ui.main;

import android.support.annotation.Nullable;

import com.nathanrjones.shiftboard.data.model.Person;
import com.nathanrjones.shiftboard.ui.base.MvpPresenter;
import com.nathanrjones.shiftboard.ui.base.MvpView;

import java.util.List;

/**
 * MVP contract for the main application screen
 */

public interface MainScreen {

    interface View extends MvpView {

        void showPersonSummary(Person person);

        void showPersonAddress(String address);

        void showPersonPhone(String phone);

        void showPersonFriends(List<Person> friends);

        void hidePersonFriends();
    }

    interface Presenter extends MvpPresenter<View> {

        void initialize(@Nullable String personId);

        void showPerson(Person person);

        void showRandomPerson();

    }

}
