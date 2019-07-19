package com.bounce;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bounce.model.LocationData;
import com.bounce.model.Request;
import com.bounce.network.ApiClient;
import com.bounce.network.NetworkService;

import java.time.LocalDateTime;
import java.util.Date;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private Button btnMissingBounce;
    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        compositeDisposable = new CompositeDisposable();
        btnMissingBounce = findViewById(R.id.btnMissingBounce);
        btnMissingBounce.setOnClickListener(view -> {
            // Reverse geocoding and get the  city for the current lat long
            Toast.makeText(this, "Your response has been recorded.", Toast.LENGTH_SHORT).show();
            Request request = createDemoRequest();
            NetworkService networkService = ApiClient.getClient(this)
                                            .create(NetworkService.class);
            DisposableSingleObserver<Boolean> disposableSingleObserver = networkService.postRequestData(request)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<Boolean>() {
                        @Override
                        public void onSuccess(Boolean aBoolean) {
                            Toast.makeText(MainActivity.this, "Sicc", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    });
            compositeDisposable.add(disposableSingleObserver);
            });

    }

    private Request createDemoRequest() {
        Request request = new Request();
        request.setUserId("UID01");
        request.setTimeStamp(new Date());
        LocationData locationData = new LocationData();
        locationData.setLati(12.2f);
        locationData.setLongi(32.6f);
        request.setLocationData(locationData);
        return request;
    }
}
