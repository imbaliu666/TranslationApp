package com.example.retrofittest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;

import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textView;

    private final static String url = "http://fy.iciba.com/";
    /**
     * 中英互译
     */
    private EditText edittext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        Button restore = findViewById(R.id.restore);
        textView = findViewById(R.id.text);
        edittext = findViewById(R.id.edittext);
        button.setOnClickListener(this);
        restore.setOnClickListener(this);

    }

    private ObservableTransformer tomain() {
        return new ObservableTransformer() {
            @Override
            public ObservableSource apply(io.reactivex.Observable upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    private void request(String text) {
        String str =null;
        try {
            String sdfdsa =new String(str.getBytes("UTF-8"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
        String urlress =text.matches("[a-zA-Z]")?"ajax.php?a=fy&f=en&t=zh&w=" + text + "/":"ajax.php?a=fy&f=zh&t=en&w=" + text + "/";

        io.reactivex.Observable<Translation> call = request.getCall(urlress);
        call.compose(tomain()).subscribe(new io.reactivex.Observer<Translation>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Translation translation) {
                textView.setText(translation.getContent().getOut());

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                request(edittext.getText().toString().trim());

                break;

            case R.id.restore:
                edittext.getText().clear();
                break;
            default:
                break;
        }
    }
}
