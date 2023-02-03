package blog.models;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.TreeMap;

public class ResponseModel {

    public ResponseModel() {
    }

    public static ResponseEntity<?> response(HttpStatus httpStatus, boolean success, Object responseObject){
        Map<String, Object> responseMap = new TreeMap<>();
        responseMap.put("Success", success);
        responseMap.put("data", responseObject);
        return new ResponseEntity<>(responseMap, httpStatus);
    }
}
