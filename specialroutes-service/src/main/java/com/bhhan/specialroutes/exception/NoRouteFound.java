package com.bhhan.specialroutes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by hbh5274@gmail.com on 2020-07-04
 * Github : http://github.com/bhhan5274
 */

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoRouteFound extends RuntimeException {
}
