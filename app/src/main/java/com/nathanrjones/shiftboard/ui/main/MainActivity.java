package com.nathanrjones.shiftboard.ui.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nathanrjones.shiftboard.R;
import com.nathanrjones.shiftboard.data.model.Person;

import java.util.List;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainScreen.View {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @Override
    public void showPersonImage(String imageUrl) {

    }

    @Override
    public void showPersonName(String name) {

    }

    @Override
    public void showPersonEmail(String email) {

    }

    @Override
    public void showPersonAddress(String addressLineOne, String addressLineTwo) {

    }

    @Override
    public void showPersonPhone(String phone) {

    }

    @Override
    public void showPersonFriends(List<Person> friends) {

    }

    @Override
    public void showLoading() {

    }
}
