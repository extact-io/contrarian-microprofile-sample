package io.extact.mp.sample.restclient.mp;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;
import org.jboss.weld.exceptions.IllegalArgumentException;

import io.extact.mp.sample.restclient.PersonClientException;
import io.extact.mp.sample.restclient.PersonClientException.ClientError;

public class PersonClientExceptionMapper implements ResponseExceptionMapper<PersonClientException> {

    // このクラスでハンドルするステータスコード
    private static final List<Integer> SHOULD_HANDLLE_ERROR =
            List.of(
                Status.NOT_FOUND.getStatusCode(),
                Status.CONFLICT.getStatusCode()
            );

    @Override
    public boolean handles(int status, MultivaluedMap<String, Object> headers) {
        // ハンドル対象のステータスコードか確認
        return SHOULD_HANDLLE_ERROR.contains(status);
    }

    @Override
    public PersonClientException toThrowable(Response response) {
        // ステータスコードに応じた例外を返す（例外変換）
        var status = Status.fromStatusCode(response.getStatus());
        switch (status) {
            case NOT_FOUND:
                return new PersonClientException(ClientError.NOT_FOUND);
            case CONFLICT:
                return new PersonClientException(ClientError.NAME_DEPULICATE);
            default:
                throw new IllegalArgumentException("unknown status:" + status);

        }
    }

}
