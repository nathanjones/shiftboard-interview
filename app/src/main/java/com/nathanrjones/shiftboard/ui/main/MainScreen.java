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

        void showPersonImage(String imageUrl);

        void showPersonName(String name);

        void showPersonEmail(String email);

        void showPersonAddress(String addressLineOne, String addressLineTwo);

        void showPersonPhone(String phone);

        void showPersonFriends(List<Person> friends);

    }

    interface Presenter extends MvpPresenter<View> {

        void initialize(@Nullable String personId);

        void showPerson(Person person);

        void showRandomPerson();

    }

}
