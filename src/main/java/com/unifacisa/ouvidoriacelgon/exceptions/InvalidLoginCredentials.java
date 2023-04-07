package com.unifacisa.ouvidoriacelgon.exceptions;

public class InvalidLoginCredentials extends RuntimeException {

    public InvalidLoginCredentials(String msg) {
        super(msg);
    }
}
