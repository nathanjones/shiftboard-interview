package com.nathanrjones.shiftboard.ui.main;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nathanrjones.shiftboard.R;
import com.nathanrjones.shiftboard.data.api.PersonApi;
import com.nathanrjones.shiftboard.data.model.Person;
import com.nathanrjones.shiftboard.data.repository.PersonRepository;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.support.design.widget.Snackbar.LENGTH_SHORT;
import static android.view.View.VISIBLE;
import static java.lang.String.format;
import static rx.schedulers.Schedulers.io;

public class MainActivity extends AppCompatActivity implements MainScreen.View {

    private MainPresenter presenter;

    @Bind(R.id.container) ViewGroup container;
    @Bind(R.id.person_image) ImageView personImage;
    @Bind(R.id.person_name) TextView personName;
    @Bind(R.id.person_email) TextView personEmail;
    @Bind(R.id.person_address) TextView personAddress;
    @Bind(R.id.person_phone) TextView personPhone;
    @Bind(R.id.person_friends) LinearLayout personFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        init();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (presenter != null) {
            presenter.detachView();
        }
    }

    @Override
    public void showPersonImage(String imageUrl) {
        Glide.with(this)
                .load(imageUrl)
                .into(personImage);
    }

    @Override
    public void showPersonName(String name) {
        personName.setText(name);
        personName.setVisibility(VISIBLE);
    }

    @Override
    public void showPersonEmail(String email) {
        personEmail.setText(email);
        personEmail.setVisibility(VISIBLE);
    }

    @Override
    public void showPersonAddress(String addressLineOne, String addressLineTwo) {
        personAddress.setText(format("%s\n%s", addressLineOne, addressLineTwo));
        personAddress.setVisibility(VISIBLE);
    }

    @Override
    public void showPersonPhone(String phone) {
        personPhone.setText(phone);
        personPhone.setVisibility(VISIBLE);
    }

    @Override
    public void showPersonFriends(List<Person> friends) {

        personFriends.removeAllViews();
        
        for (Person friend : friends) {

            TextView personView = new TextView(this);

            personView.setText(friend.getFirstName());
            personView.setOnClickListener(v -> {

            });

            personFriends.addView(personView);
        }
    }

    @Override
    public void showLoading() {
        Snackbar.make(container, "Loading...", LENGTH_SHORT).show();
    }

    @OnClick(R.id.person_image)
    void onLoadNewPersonClicked() {
        presenter.showRandomPerson();
    }

    /**
     * Initialize the presenter
     */
    private void init() {

        if (presenter == null) {
            presenter = buildPresenter();
        }

        presenter.attachView(this);
        presenter.initialize(getPersonIdArg());
    }

    /**
     * For a project any larger, I would use Dagger to resolve the dependencies
     */
    private MainPresenter buildPresenter() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://interview--api-shiftboard-com-su4kveu0j5kj.runscope.net")
                .client(
                        new OkHttpClient.Builder()
                                .addInterceptor(chain -> {

                                    Request request = chain.request();

                                    Log.d("Retrofit", "Requesting: " + request.url());

                                    return chain.proceed(request);
                                })
                                .build()
                )
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(io()))
                .build();

        PersonApi personApi = retrofit.create(PersonApi.class);

        PersonRepository personRepo = new PersonRepository(personApi);

        return new MainPresenter(personRepo);
    }


    private String getPersonIdArg() {
        return null; // TODO: retrieve from intent args
    }

}
