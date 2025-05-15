package proton.face.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private String status;
    private String message;
    private Object data;

    public static Response success(String message,Object data) {
        return new Response("Success", message, data);
    }
    public static Response error(String message) {
        return new Response("Error", message, null);
    }
}
