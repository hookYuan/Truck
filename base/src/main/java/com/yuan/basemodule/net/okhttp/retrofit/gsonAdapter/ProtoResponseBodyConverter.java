package com.yuan.basemodule.net.okhttp.retrofit.gsonAdapter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by YuanYe on 2017/1/24 0024.
 */

public class ProtoResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    ProtoResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        //TODO 可以统一对返回进行处理
        JsonReader jsonReader = gson.newJsonReader(value.charStream());

        try {
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }
    }
}