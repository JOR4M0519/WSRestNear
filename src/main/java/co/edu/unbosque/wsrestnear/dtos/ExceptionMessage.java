package co.edu.unbosque.wsrestnear.dtos;

public class ExceptionMessage {

    private int errorCode;
    private String message;

    //Método constructor de la clase ExceptionMessage y inicializa las variables declaradas con las pasadas por parámetros
    public ExceptionMessage(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    //Obtiene el valor correspondiente a la variable errorCode
    public int getErrorCode() {
        return errorCode;
    }

    //Asigna un valor a la variable errorCode
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    //Obtiene el valor correspondiente a la variable message
    public String getMessage() {
        return message;
    }

    //Asigna un valor a la variable message
    public void setMessage(String message) {
        this.message = message;
    }
}