package com.bounce;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bounce.model.LocationData;
import com.bounce.model.Request;
import com.bounce.network.ApiClient;
import com.bounce.network.NetworkService;
import com.bounce.service.ReverseGeocodingService;
import com.ornach.nobobutton.NoboButton;

import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private NoboButton btnMissingBounce;
    //private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //compositeDisposable = new CompositeDisposable();
        btnMissingBounce = findViewById(R.id.btnMissingBounce);
        btnMissingBounce.setOnClickListener(view -> {
            // Reverse geo-coding and get the  city for the current lat long
            // Toast.makeText(this, "Your response has been recorded.", Toast.LENGTH_SHORT).show();
            // Request request = createDemoRequest();
            NetworkService networkService = ApiClient.getClient(this)
                                            .create(NetworkService.class);
            networkService.postRequestData(10)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableSingleObserver<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(MainActivity.this, "Your request is registered. Hope to provide a bounce here!!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(MainActivity.this, "Your request is registered. Hope to provide a bounce here!!", Toast.LENGTH_SHORT).show();
                        }
                    });
            });

    }

    private Request createDemoRequest() {
        Request request = new Request();
        request.setUserId("UID01");
        //request.setTimeStamp(new Date());
        int lati = new Random().nextInt(100), longi = new Random().nextInt(100);
        LocationData locationData = new LocationData();
        locationData.setCity(ReverseGeocodingService.getCity(lati, longi));
        request.setLocationData(locationData);
        return request;
    }
}
