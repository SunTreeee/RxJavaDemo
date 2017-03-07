package com.sun.myone.rxdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onRequest("http://www.baidu.com/", new ResponseListener() {
            @Override
            public void onSucceed(ResponseBody responseBody) throws IOException {
                Toast.makeText(MainActivity.this, "获取成功", Toast.LENGTH_SHORT).show();
                Log.d("MainActivity", responseBody.string());
            }

            @Override
            public void onError() {
                Log.d("MainActivity", "获取失败，请检查网络是否畅通");
                Toast.makeText(MainActivity.this, "获取失败，请检查网络是否畅通", Toast.LENGTH_SHORT).show();
            }
        });



    }
    private void onRequest(String url, final ResponseListener listener){
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        BaiDuService service = retrofit.create(BaiDuService.class);
        service.getText()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        //                        Toast.makeText(MainActivity.this, "获取成功", Toast.LENGTH_SHORT).show();
                        //                        try {
                        //                            System.out.println(responseBody.string());
                        //                        } catch (IOException e) {
                        //                            e.printStackTrace();
                        //                        }
                        try {
                            listener.onSucceed(responseBody);
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.d("MainActivity", "sss");
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        //                        t.printStackTrace();
                        //                        Toast.makeText(MainActivity.this, "获取失败，请检查网络是否畅通", Toast.LENGTH_SHORT).show();
                        listener.onError();
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("任务结束");
                    }
                });
//
//        Flowable<String> flowable = Flowable.create(new FlowableOnSubscribe<String>() {
//            @Override
//            public void subscribe(FlowableEmitter<String> e) throws Exception {
//                e.onNext("hello, rxjava");
//                e.onComplete();
//            }
//        },BackpressureStrategy.BUFFER);
//        List<String> list = new ArrayList<>();
//        list.add("我");
//        list.add("最");
//        list.add("帅");
//        Flowable.just(list).subscribe(new Consumer<List<String>>() {
//            @Override
//            public void accept(@NonNull List<String> strings) throws Exception {
//                Observable.fromIterable(strings).subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(@NonNull String s) throws Exception {
//
//                    }
//                });
//            }
//        });
//        Flowable.just(list)
//                .flatMap(new Function<List<String>, Publisher<String>>() {
//                    @Override
//                    public Publisher<String> apply(@NonNull List<String> strings) throws Exception {
//                        return Flowable.fromIterable(strings);
//                    }
//                }).subscribe(new Consumer<String>() {
//            @Override
//            public void accept(@NonNull String s) throws Exception {
//
//            }
//        });
    }

//    public static void main(String[] args) {
//        Flowable.fromArray(1,2,3,4).take(2).subscribe(new Consumer<Integer>() {
//            @Override
//            public void accept(@NonNull Integer integer) throws Exception {
//                System.out.println(integer);
//            }
//        });
//    }
}
