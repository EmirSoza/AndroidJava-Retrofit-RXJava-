package com.emirhan.retrofitcurrency.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.emirhan.retrofitcurrency.R;
import com.emirhan.retrofitcurrency.adapter.RecyclerViewAdapter;
import com.emirhan.retrofitcurrency.model.CryptoModel;
import com.emirhan.retrofitcurrency.service.CryptoApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ArrayList<CryptoModel> cryptoModels;
    private String BASE_URL = "https://api.nomics.com/v1/";
    Retrofit retrofit;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    CompositeDisposable compositeDisposable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        //https://api.nomics.com/v1/prices?key=78396624aded6a7a1cf098f57c1dc8a5
        Gson gson = new GsonBuilder().setLenient().create();
        //RxJava eklenirse sadece addCallAdapter i unutmamak gerekiyor
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL).addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        loadData();

    }
    private void loadData(){
        CryptoApi cryptoApi = retrofit.create(CryptoApi.class);
        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(cryptoApi.getData()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse));

        /* without RxJava
        Call<List<CryptoModel>> call = cryptoApi.getData();
        call.enqueue(new Callback<List<CryptoModel>>() {
            @Override
            public void onResponse(Call<List<CryptoModel>> call, Response<List<CryptoModel>> response) {
                if(response.isSuccessful()){
                    List<CryptoModel> responseList = response.body();
                    cryptoModels = new ArrayList<>(responseList);
                    //RecyclerView and Adapter
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerViewAdapter = new RecyclerViewAdapter(cryptoModels);
                    recyclerView.setAdapter(recyclerViewAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<CryptoModel>> call, Throwable t) {
                t.printStackTrace();
            }
        }); */
    }
    private void handleResponse(List<CryptoModel> cryptoModelList){
        cryptoModels = new ArrayList<>(cryptoModelList);
        //RecyclerView and Adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerViewAdapter = new RecyclerViewAdapter(cryptoModels);
        recyclerView.setAdapter(recyclerViewAdapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}