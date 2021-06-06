package com.taskagile.web.results;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;

public class ApiResult extends HashMap<String, Object> {
    private static final long serialVersionUID = 2297234893453849562L;

    private static final String MESSAGE_KEY = "message";
    private static final String ERROR_CODE_KEY = "errorReferenceCode";

    public static ApiResult blank(){
        return new ApiResult();
    }

    public static ApiResult message(@NotBlank String message){
        ApiResult apiResult = new ApiResult();
        apiResult.put(MESSAGE_KEY, message);

        return apiResult;
    }

    /*
    *  ex.
    *  ApiResult result = ApiResult().blank().add("key1", 12).add("key2", 34)
    * */
    public ApiResult add(@NotBlank String key, @NotNull Object value){
        this.put(key, value);
        return this;
    }

}
