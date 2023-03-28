package in.limebrew.xpenseservice.utils;

import in.limebrew.xpenseservice.entity.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseUtil {
    public static final int HTTP_STATUS_OK = 200;
    public static final int HTTP_STATUS_CREATED = 201;
    public static final int HTTP_STATUS_UPDATED = 201;
    public static final int HTTP_STATUS_DELETED = 201;
    public static final int HTTP_STATUS_BAD_REQUEST = 400;
    public static final int HTTP_STATUS_UNAUTHORIZED = 403;
    public static final int HTTP_STATUS_NOT_FOUND = 404;
    public static final int HTTP_STATUS_LIMIT_EXCEEDED = 429;
    public static final int HTTP_STATUS_INTERNAL_SERVER_ERROR = 500;
    public static final int HTTP_STATUS_PARSING_ERROR = 500;

    //? Success GetDashboard
    public static ResponseEntity<Map<String,Object>> handleDashboardInfo(Map<String,Object> dashboardInfo){
        Map<String,Object> response = new HashMap<>();

        if(dashboardInfo.isEmpty()){
            dashboardInfo.put("netEarnings",String.format("%.2f",0.0f));
            dashboardInfo.put("netExpenses",String.format("%.2f",0.0f));
            dashboardInfo.put("netInvestments",String.format("%.2f",0.0f));
            dashboardInfo.put("netFundTransfer",String.format("%.2f",0.0f));
            dashboardInfo.put("netSavings", String.format("%.2f",0.0f));
        }

        response.put("status",HTTP_STATUS_OK);
        response.put("message","Dashboard info");
        response.put("data",dashboardInfo);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    //? Success GetOne
    public static ResponseEntity<Map<String,Object>> successGetOne(Transaction transaction){
        Map<String,Object> response = new HashMap<>();
        response.put("status",HTTP_STATUS_OK);
        response.put("message","Item found");
        response.put("data",transaction);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //? Success GetMany
    public static ResponseEntity<Map<String,Object>> successGetMany(List<Transaction> transactionList){
        Map<String,Object> response = new HashMap<>();
        response.put("status",HTTP_STATUS_OK);
        response.put("message",String.format("%s item(s) found",transactionList.size()));
        response.put("data",transactionList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //? Success AddOne
    public static ResponseEntity<Map<String,Object>> successAddOne(){
        Map<String,Object> response = new HashMap<>();
        response.put("status",HTTP_STATUS_CREATED);
        response.put("message","Item added successfully");
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    //? Success UpdateOne
    public static ResponseEntity<Map<String,Object>> successUpdateOne(){
        Map<String,Object> response = new HashMap<>();
        response.put("status",HTTP_STATUS_UPDATED);
        response.put("message","Item updated successfully");
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    //? Success DeleteOne
    public static ResponseEntity<Map<String,Object>> successDeleteOne(){
        Map<String,Object> response = new HashMap<>();
        response.put("status",HTTP_STATUS_DELETED);
        response.put("message","Item deleted successfully");
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    //? Error Bad Request
    public static ResponseEntity<Map<String,Object>> errorBadRequest(){
        Map<String,Object> response = new HashMap<>();
        response.put("status",HTTP_STATUS_BAD_REQUEST);
        response.put("error","Bad Request!");
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    //? Error Unauthorized
    public static ResponseEntity<Map<String,Object>> errorUnauthorized(){
        Map<String,Object> response = new HashMap<>();
        response.put("status",HTTP_STATUS_UNAUTHORIZED);
        response.put("error","Unauthorized! Invalid token");
        return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
    }

    //? Error Not Found
    public static ResponseEntity<Map<String,Object>> errorNotFound(){
        Map<String,Object> response = new HashMap<>();
        response.put("status",HTTP_STATUS_NOT_FOUND);
        response.put("error","Error not found!");
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    //? Error Internal Server
    public static ResponseEntity<Map<String,Object>> errorInternalServer(){
        Map<String,Object> response = new HashMap<>();
        response.put("status",HTTP_STATUS_INTERNAL_SERVER_ERROR);
        response.put("error","Internal Server Error!");
        return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //? Error Internal Server
    public static ResponseEntity<Map<String,Object>> errorLimitExceeded(){
        Map<String,Object> response = new HashMap<>();
        response.put("status",HTTP_STATUS_LIMIT_EXCEEDED);
        response.put("error","Query Limit Exceeded!");
        return new ResponseEntity<>(response,HttpStatus.TOO_MANY_REQUESTS);
    }

    //? Error Parsing entity
    public static ResponseEntity<Map<String,Object>> errorParsingEntity(String errorMsg){
        Map<String,Object> response = new HashMap<>();
        response.put("status",HTTP_STATUS_PARSING_ERROR);
        response.put("error",errorMsg);
        return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
