package io.extact.mp.sample.restclient;

public class PersonClientException extends RuntimeException {

    private ClientError clientError;

    public PersonClientException(ClientError clientError) {
        this.clientError = clientError;
    }

    public ClientError getClientError() {
        return clientError;
    }

    public enum ClientError {
        NOT_FOUND,
        NAME_DEPULICATE
    }
}
