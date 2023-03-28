package in.limebrew.xpenseservice.constants;

public record ResponseRecord(int status,String message, Object data) {
}

record ResponseErrorRecord(int status, String error){}