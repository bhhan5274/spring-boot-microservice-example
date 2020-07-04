package com.bhhan.utils.hystrix;

import com.bhhan.utils.usercontext.UserContext;
import com.bhhan.utils.usercontext.UserContextHolder;

import java.util.concurrent.Callable;

/**
 * Created by hbh5274@gmail.com on 2020-07-04
 * Github : http://github.com/bhhan5274
 */
public class DelegatingUserContextCallable<V> implements Callable<V> {

    private final Callable<V> delegate;
    private UserContext originalUserContext;

    public DelegatingUserContextCallable(Callable<V> delegate, UserContext originalUserContext) {
        this.delegate = delegate;
        this.originalUserContext = originalUserContext;
    }

    @Override
    public V call() throws Exception {
        UserContextHolder.setContext(originalUserContext);

        try {
            return delegate.call();
        }finally {
            this.originalUserContext = null;
        }
    }

    public static <V> Callable<V> create(Callable<V> delegate, UserContext userContext){
        return new DelegatingUserContextCallable<>(delegate, userContext);
    }
}
