package com.nathanrjones.shiftboard.data.repository;

import com.nathanrjones.shiftboard.data.api.PersonApi;
import com.nathanrjones.shiftboard.data.model.Person;

import rx.Observable;

public class PersonRepository {

    private final PersonApi personApi;

    public PersonRepository(PersonApi personApi) {
        this.personApi = personApi;
    }

    public Observable<Person> getRandomPerson() {
        return personApi.getPerson();
    }

    public Observable<Person> getPerson(String personId) {
        return personApi.getPerson(personId);
    }

}
