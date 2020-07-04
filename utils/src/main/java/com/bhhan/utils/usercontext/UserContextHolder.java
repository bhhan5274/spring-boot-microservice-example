package com.bhhan.utils.usercontext;

import org.springframework.util.Assert;

import java.util.Objects;

/**
 * Created by hbh5274@gmail.com on 2020-07-03
 * Github : http://github.com/bhhan5274
 */
public class UserContextHolder {
    private static final ThreadLocal<UserContext> context = new ThreadLocal<UserContext>();

    public static UserContext getContext(){
        UserContext userContext = UserContextHolder.context.get();

        if(Objects.isNull(userContext)){
            userContext = createEmptyContext();
            context.set(userContext);
        }
        return context.get();
    }

    public static void setContext(UserContext userContext){
        Assert.notNull(userContext, "Only non-null UserContext instances are permitted");
        context.set(userContext);
    }

    private static UserContext createEmptyContext() {
        return new UserContext();
    }
}
