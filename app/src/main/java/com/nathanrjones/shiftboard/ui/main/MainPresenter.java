package com.nathanrjones.shiftboard.ui.main;

import android.support.annotation.Nullable;
import android.util.Log;

import com.nathanrjones.shiftboard.data.model.Person;
import com.nathanrjones.shiftboard.data.repository.PersonRepository;

import rx.Observable;

import static java.lang.String.format;
import static rx.android.schedulers.AndroidSchedulers.mainThread;
import static rx.schedulers.Schedulers.io;

public class MainPresenter implements MainScreen.Presenter {

    private static final String TAG = MainPresenter.class.getName();

    private Person person;

    private MainScreen.View view;

    private final PersonRepository personRepo;

    MainPresenter(PersonRepository personRepo) {
        this.personRepo = personRepo;
    }

    @Override
    public void attachView(MainScreen.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Nullable
    @Override
    public MainScreen.View getView() {
        return view;
    }

    @Override
    public void initialize(@Nullable String personId) {
        loadPerson(personId);
    }

    @Override
    public void showPerson(Person friend) {

        personRepo.getPerson(friend.getId())
                .observeOn(mainThread())
                .subscribe(person -> {
                    displayPerson(person);
                }, e -> {
                    Log.e(TAG, "Could not load friend", e);
                    e.printStackTrace();
                });
    }

    @Override
    public void showRandomPerson() {
        loadPerson(null);
    }

    private void loadPerson(@Nullable String id) {

        getPerson(id)
                .observeOn(mainThread())
                .subscribeOn(io())
                .subscribe(person ->  {

                    displayPerson(person);

                }, e -> {
                    Log.e(TAG, "Could not load person", e);
                    e.printStackTrace();
                });
    }

    private void displayPerson(Person person) {

        if (getView() == null) return;

        getView().showPersonSummary(person);
        getView().showPersonAddress(
                person.getAddressStreet(),
                format("%s, %s %s",
                        person.getAddressCity(),
                        person.getAddressState(),
                        person.getAddressZipcode()
                )
        );
        getView().showPersonPhone(person.getPhoneNumber());
        getView().showPersonFriends(person.getFriends());

    }

    private Observable<Person> getPerson(@Nullable String id) {
        return isNotEmpty(id) ? personRepo.getPerson(id) : personRepo.getRandomPerson();
    }

    private boolean isNotEmpty(String s) {
        return s != null && !s.isEmpty();
    }
}
