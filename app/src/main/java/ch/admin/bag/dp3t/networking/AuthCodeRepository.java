/*
 * Copyright (c) 2020 Ubique Innovation AG <https://www.ubique.ch>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */
package ch.admin.bag.dp3t.networking;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.IOException;

import ch.admin.bag.dp3t.networking.errors.InvalidCodeError;
import ch.admin.bag.dp3t.networking.errors.ResponseError;
import ch.admin.bag.dp3t.networking.models.AuthenticationCodeRequestModel;
import ch.admin.bag.dp3t.networking.models.AuthenticationCodeResponseModel;
import ch.admin.bag.dp3t.BuildConfig;

import org.dpppt.android.sdk.backend.ResponseCallback;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthCodeRepository {

    private AuthCodeService authCodeService;

    public AuthCodeRepository(@NonNull Context context) {

        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.addInterceptor(chain -> {
            Request request = chain.request()
                    .newBuilder()
                    .build();
            return chain.proceed(request);
        });

        int cacheSize = 5 * 1024 * 1024; // 5 MB
        Cache cache = new Cache(context.getCacheDir(), cacheSize);
        okHttpBuilder.cache(cache);

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(BuildConfig.AUTH_CODE_URL);

        if(BuildConfig.DEBUG) {
			HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
			loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
			retrofitBuilder.client(okHttpBuilder.addInterceptor(loggingInterceptor).build());
		}

        Retrofit retrofit = retrofitBuilder.addConverterFactory(GsonConverterFactory.create())
                .build();

        authCodeService = retrofit.create(AuthCodeService.class);
    }

    public void getAccessToken(@NonNull AuthenticationCodeRequestModel authenticationCode,
                               @NonNull ResponseCallback<AuthenticationCodeResponseModel> callbackListener) {
        authCodeService.getAccessToken(authenticationCode.getAuthorizationCode()).enqueue(new Callback<Response<String>>() {
            @Override
            public void onResponse(Call<Response<String>> call, Response<Response<String>> response) {
                if (response.isSuccessful()) {

                    AuthenticationCodeResponseModel authenticationCodeResponseModel = new AuthenticationCodeResponseModel();
                    authenticationCodeResponseModel.setAccessToken(response.headers().get("X-OTP"));
                    callbackListener.onSuccess(authenticationCodeResponseModel);
                } else {
                    if (response.code() == 404) {
                        onFailure(call, new InvalidCodeError());
                    } else {
                        onFailure(call, new ResponseError(response.raw()));
                    }
                }
            }

            @Override
            public void onFailure(Call<Response<String>> call, Throwable t) {
                callbackListener.onError(t);
            }
        });
    }

    public AuthenticationCodeResponseModel getAccessTokenSync(@NonNull AuthenticationCodeRequestModel authenticationCode)
            throws IOException, ResponseError {
        Response response = authCodeService.getAccessToken(authenticationCode.getAuthorizationCode()).execute();
        if (!response.isSuccessful()) throw new ResponseError(response.raw());
        AuthenticationCodeResponseModel authenticationCodeResponseModel = new AuthenticationCodeResponseModel();
        authenticationCodeResponseModel.setAccessToken(response.headers().get("X-OTP"));

        return authenticationCodeResponseModel;
    }

}
