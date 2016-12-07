package com.nathanrjones.shiftboard.data.api;

import com.nathanrjones.shiftboard.data.model.Person;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface PersonApi {

    /**
     * Get a random person
     */
    @GET("/person")
    Observable<Person> getPerson();

    /**
     * Get a person given their ID
     *
     * @param personId the ID of the person
     *
     * @return the profile of the provided person
     */
    @GET("/person/{id}")
    Observable<Person> getPerson(@Path("id") String personId);

}
