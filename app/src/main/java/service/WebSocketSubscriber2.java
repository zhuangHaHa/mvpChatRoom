package service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by dhh on 2018/4/9.
 * <p>
 * 功能：可根据传入的泛型，直接将String类型的text转化成实体类
 * </P>
 *
 * @author dhh
 */
public abstract class WebSocketSubscriber2<T> extends WebSocketSubscriber {
    private static final Gson GSON = new Gson();
    protected Type type;


    public WebSocketSubscriber2() {
        analysisType();
    }

    private void analysisType() {
        Type superclass = getClass().getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("No generics found!");
        }
        ParameterizedType type = (ParameterizedType) superclass;
        this.type = type.getActualTypeArguments()[0];
    }

    @Override
    @CallSuper
    protected void onMessage(@NonNull String text) {
        Observable.just(text)
                .map(new Function<String, Object>() {
                    @Override
                    public Object apply(String s) throws Exception {
                        try {
                            return GSON.fromJson(s, type);
                        } catch (JsonSyntaxException e) {
                            return GSON.fromJson(GSON.fromJson(s, String.class), type);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
//                .subscribe(new Consumer<T>() {
//                    @Override
//                    public void accept(T t) throws Exception {
//                        onMessage(t);
//                    }
//                });
    }

    protected abstract void onMessage(T t);
}
