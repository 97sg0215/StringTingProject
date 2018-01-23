package com.string.leeyun.stringting_android;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.string.leeyun.stringting_android.API.Rest_ApiService;
import com.string.leeyun.stringting_android.API.check_login;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.string.leeyun.stringting_android.API.Rest_ApiService.API_URL;
import static com.string.leeyun.stringting_android.R.mipmap.e;
import static com.string.leeyun.stringting_android.R.mipmap.t;


/**
 * Created by leeyun on 2017. 9. 8..
 */

/**
 * Created by leeyun on 2017. 8. 24..
 */
public class Login_local extends Activity {

    Rest_ApiService apiService;
    Retrofit retrofit;
    int account_id;
    String token;
    String sex;
    String Edit_id;
    String Edit_pw;
    String refreshedToken;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.login_local);


        }


    public void onClick_login_check(View v){



        SharedPreferences pref = getSharedPreferences("Local_DB",MODE_PRIVATE);
        sex=pref.getString("sex","notfound");
        Log.e("sex",sex);
        account_id = pref.getInt("account_id",0);
        Log.e("local_account", String.valueOf(account_id));
        token=pref.getString("token","?");
        Log.e("loacal_token",String.valueOf(token));
        String fcm_token=pref.getString("fcm_token","?");



        OkHttpClient.Builder client1 = new OkHttpClient.Builder();
        client1.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {

                Request builder = chain.request();
                Request newRequest;


                newRequest = builder.newBuilder()
                        .addHeader("access-token"," ")
                        .build();


                return chain.proceed(newRequest);

            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client1.build())
                .build();
        apiService= retrofit.create(Rest_ApiService.class);

        EditText test_ID= (EditText)findViewById(R.id.id_Edit);

        Edit_id=test_ID.getText().toString();
        Log.e("Edit_id",Edit_id);
        EditText pw=(EditText)findViewById(R.id.pw_edit);
        Edit_pw=pw.getText().toString();


        check_login CheckLogin=new check_login();

        CheckLogin.setEmail(Edit_id);
        CheckLogin.setPassword(Edit_pw);
        CheckLogin.setFcm_token(fcm_token);

        Log.e("checkloginsex",sex);
        Log.e("checkloginfcm",fcm_token);

        try {
            Call<check_login> post_check_login = apiService.post_check_login(CheckLogin);
            post_check_login.enqueue(new Callback<check_login>() {
                @Override
                public void onResponse(Call<check_login> call, Response<check_login> response) {


                    Intent intent_activate = new Intent(Login_local.this,  TabbedBar.class);
                    Intent intent_ghost= new Intent(Login_local.this, Basicinfo_Edit.class);
                    Intent intent_interview=new Intent(Login_local.this, Mediate.class);
                    check_login gsonresponse = response.body();
                    Log.e("onresponse_check_login", gsonresponse.getResult());
                    Log.e("onresponse_check_login",gsonresponse.getMessage());
                    try{
                        int account_id= Integer.parseInt(gsonresponse.getId());
                        String sex=gsonresponse.getSex();
                        get_total_data_save(gsonresponse.getToken(), Integer.parseInt(gsonresponse.getId()),gsonresponse.getSex());

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    if (gsonresponse.getStatus()==null) {

                        Log.e("get_status","등록되지않은 이메일입니다");
                        intent_ghost.putExtra("ID",Edit_id);
                        intent_ghost.putExtra("PW",Edit_pw);
                        intent_ghost.putExtra("setformat","EMAIL");
                        intent_ghost.putExtra("token",token);
                        startActivity(intent_ghost);
                    }
                    else
                    {
                        Log.e("onresponse", gsonresponse.getStatus());
                        Log.e("onresponse", String.valueOf(response.code()));
                        Log.e("onresponse", "success");

                        if (gsonresponse.getStatus().equals("ACTIVATE"))
                        {

                            startActivity(intent_activate);
                        }
                        else if(gsonresponse.getStatus().equals("GHOST")){
                            intent_ghost.putExtra("ID",Edit_id);
                            intent_ghost.putExtra("PW",Edit_pw);
                            intent_ghost.putExtra("setformat","EMAIL");
                            intent_ghost.putExtra("token",token);

                            startActivity(intent_ghost);
                        }
                        else if(gsonresponse.getStatus().equals("INREVIEW")){
                            startActivity(intent_interview);
                        }
                        else if(gsonresponse.getStatus().equals("REJECTED")){

                        }
                        else{
                            intent_ghost.putExtra("ID",Edit_id);
                            intent_ghost.putExtra("PW",Edit_pw);
                            Log.e("id",Edit_id);
                            intent_ghost.putExtra("setformat","EMAIL");
                            intent_ghost.putExtra("token",token);

                            startActivity(intent_ghost);
                        }
                    }




                }


                @Override
                public void onFailure(Call<check_login> call, Throwable t) {
                    Log.d("sam", t.toString());
                }

            });
        }catch (Exception e){
            e.printStackTrace();
        }




    }
    public void onClick_back(View v){
        super.onBackPressed(); // or super.finish();

    }

    public void get_total_data_save(String token,int account_id,String sex){
        SharedPreferences pref = getSharedPreferences("Local_DB", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("token",token);
        editor.putInt("account_id",account_id);
        editor.putString("sex",sex);
        editor.clear();
        editor.commit();
    }


}
