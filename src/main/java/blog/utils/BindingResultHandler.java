package blog.utils;

import org.springframework.validation.BindingResult;

import java.util.TreeMap;

public class BindingResultHandler {

    public static TreeMap<String, Object> getErrorData(BindingResult bindingResult){
        TreeMap<String, Object> errorData = new TreeMap<>();
        bindingResult.getFieldErrors().stream()
                .forEach(fieldError -> errorData.put(fieldError.getField(), fieldError.getDefaultMessage()));
        return errorData;
    }

}
