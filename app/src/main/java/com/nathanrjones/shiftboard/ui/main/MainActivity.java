package com.nathanrjones.shiftboard.ui.main;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nathanrjones.shiftboard.R;
import com.nathanrjones.shiftboard.data.api.PersonApi;
import com.nathanrjones.shiftboard.data.model.Person;
import com.nathanrjones.shiftboard.data.repository.PersonRepository;
import com.nathanrjones.shiftboard.ui.view.PersonSummaryView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.support.design.widget.Snackbar.LENGTH_SHORT;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.nathanrjones.shiftboard.ui.view.PersonSummaryView.Style.Large;
import static rx.schedulers.Schedulers.io;

public class MainActivity extends AppCompatActivity implements MainScreen.View {

    private MainPresenter presenter;

    @Bind(R.id.container) ViewGroup container;
    @Bind(R.id.person_summary) PersonSummaryView personSummary;
    @Bind(R.id.person_image) ImageView personImage;
    @Bind(R.id.person_name) TextView personName;
    @Bind(R.id.person_email) TextView personEmail;
    @Bind(R.id.person_address) TextView personAddress;
    @Bind(R.id.person_phone) TextView personPhone;
    @Bind(R.id.person_friends) LinearLayout personFriends;
    @Bind(R.id.container_friends) ViewGroup containerFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setTitle("People With Friends");

        personSummary.setStyle(Large);
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
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_random:
                presenter.showRandomPerson();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showPersonSummary(Person person) {
        personSummary.setPerson(person);
    }

    @Override
    public void showPersonAddress(String address) {
        personAddress.setText(address);
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

            PersonSummaryView personView = new PersonSummaryView(this);

            personView.setPerson(friend);
            personView.setOnClickListener(v -> {
                presenter.showPerson(friend);
            });

            personFriends.addView(personView);
        }

        containerFriends.setVisibility(VISIBLE);
    }

    @Override
    public void hidePersonFriends() {
        personFriends.removeAllViews();
        containerFriends.setVisibility(GONE);
    }

    @Override
    public void showLoading() {
        Snackbar.make(container, "Loading...", LENGTH_SHORT).show();
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
