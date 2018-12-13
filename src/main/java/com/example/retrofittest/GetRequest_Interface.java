package com.example.retrofittest;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * 项目名： Android new
 * 包名：   com.example.retrofittest
 * 文件名： GetRequest_Interface
 * 创建者： LFY
 * 创建时间： 2018/12/11 16:06
 * 描述：   TODO
 */

public interface GetRequest_Interface {
    void Text();
    @POST()
    Observable<Translation> getCall(@Url String url);
}
