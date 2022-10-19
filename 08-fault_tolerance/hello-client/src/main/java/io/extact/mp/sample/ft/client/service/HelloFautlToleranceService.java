package io.extact.mp.sample.ft.client.service;

import java.net.SocketException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.eclipse.microprofile.faulttolerance.Asynchronous;
import org.eclipse.microprofile.faulttolerance.Bulkhead;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.extact.mp.sample.ft.client.exception.FatalException;
import io.extact.mp.sample.ft.client.exception.RetryableException;
import io.extact.mp.sample.ft.client.exception.SkipException;
import io.extact.mp.sample.ft.client.external.HelloRestClient;

@ApplicationScoped
public class HelloFautlToleranceService {
    private static final Logger log = LoggerFactory.getLogger(HelloFautlToleranceService.class);
    private HelloRestClient helloClient;

    @Inject
    public HelloFautlToleranceService(@RestClient HelloRestClient helloClient) {
        this.helloClient = helloClient;
    }

    /**
     * 500msec経ったらタイムアウトさせる
     * <pre>
     * curl localhost:7001/client/01
     * </pre>
     */
    @Timeout(500)
    public String hello_timeout() {
        return helloClient.hello("sleep");
    }

    /**
     * 500msec経ったらタイムアウトさせるが失敗した場合ははリトライを3回行う。
     * <pre>
     * curl localhost:7001/client/02
     * </pre>
     */
    @Timeout(500)
    @Retry(maxRetries = 3)
    public String hello_retry_by_timeout() {
        return helloClient.hello("sleep");
    }

    /**
     * 500msec経ったらタイムアウトさせるが失敗した場合ははリトライを3回行う。
     * リトライは1秒間空けてから実行する。
     * <pre>
     * curl localhost:7001/client/04
     * </pre>
     */
    @Timeout(500)
    @Retry(maxRetries = 3, delay = 1000)
    public String hello_retry_with_delay() {
        return helloClient.hello("sleep");
    }

    /**
     * 500msec経ったらタイムアウトさせるが失敗した場合ははリトライを3回行う。
     * リトライは0-2秒のランダムな間隔を空けてから実行する。
     * <pre>
     * curl localhost:7001/client/04
     * </pre>
     */
    @Timeout(500)
    @Retry(maxRetries = 3, delay = 1000, jitter = 1000)
    public String hello_retry_with_jitterDelay() {
        return helloClient.hello("sleep");
    }

    /**
     * 500msec経ったらタイムアウトさせるが失敗した場合はリトライを3回行う。
     * リトライは0-1秒のランダムな間隔を空けてから実行する。
     * ただし、リトライを行うのはトータルで4秒以内。初回の実行から4秒を経過した場合リトライは行わない。
     * <pre>
     * curl localhost:7001/client/05
     * </pre>
     */
    @Timeout(500)
    @Retry(maxRetries = 3, delay = 0, jitter = 1000, maxDuration = 4000)
    public String hello_retry_with_maxDuration() {
        return helloClient.hello("sleep");
    }

    /**
     * RetryableExceptionにより処理が失敗したら成功するまで3回リトライを行う。
     * <pre>
     * curl localhost:7001/client/06
     * </pre>
     */
    @Retry(maxRetries = 3, retryOn = {RetryableException.class})
    public String hello_retryOn_retryable() {
        return helloClient.hello("throwRetryable");
    }

    /**
     * Exception発生に処理が失敗したら成功するまで3回リトライを行う。
     * ただし、FatalExceptionおよびそのサブクラスの場合はリトライは行わずその時点で処理を失敗させる。
     * <pre>
     * curl localhost:7001/client/07
     * </pre>
     */
    @Retry(maxRetries = 3, abortOn = {FatalException.class})
    public String hello_abortOn() {
        return helloClient.hello("throwFatal");
    }

    /**
     * SocketExceptionにより処理が失敗したら成功するまで3回リトライを行う。
     * SocketExceptionはcauseに含まれるため再帰的に辿って有無を確認する必要がある
     * <pre>
     * サービスを落としてSocketExceptionが発生するようにする
     * curl localhost:7001/client/07
     * </pre>
     */
    @Retry(maxRetries = 3, retryOn = {SocketException.class})
    public String hello_socketException() throws SocketException {
        try {
            log.info("Execute:hello_08_socketException()");
            return helloClient.hello("throwFatal");
        } catch (Exception original) {
            Throwable test = original;
            while (!(test instanceof SocketException)) {
                test = test.getCause();
                if (test == null) {
                    throw original;
                }
            }
            throw (SocketException) test;
        }
    }

    /**
     * リトライしても処理が成功しなかった場合は指定された代替処理を行う。
     * <pre>
     * curl localhost:7001/client/09
     * </pre>
     */
    @Timeout(500)
    @Retry(maxRetries = 1)
    @Fallback(HelloFallbackService.class)
    public String hello_fallback_by_cdi() {
        return helloClient.hello("throwRetryable");
    }

    /**
     * リトライしても処理が成功しなかった場合は指定された代替処理を行う。
     * <pre>
     * curl localhost:7001/client/10
     * </pre>
     */
    @Timeout(500)
    @Retry(maxRetries = 1)
    @Fallback(fallbackMethod = "fallbackForHello")
    public String hello_fallback_by_inline() {
        return helloClient.hello("throwRetryable");
    }
    @SuppressWarnings("unused")
    private String fallbackForHello() {
        return "沈黙..";
    }

    /**
     * 処理が失敗しても特定の例外では代替処理を行わないようにする。
     * <pre>
     * curl localhost:7001/client/11
     * </pre>
     */
    @Retry(maxRetries = 1)
    @Fallback(fallbackMethod = "fallbackForHello", applyOn = { Exception.class }, skipOn = { FatalException.class })
    public String hello_fallback_with_criteria() {
        return helloClient.hello("throwFatal");
    }

    /**
     * ある頻度で処理が失敗するような場合は一定時間処理の実行を遮断し、その後も一定の回数連続で処理がするまでは
     * 処理の遮断を繰り行うようにする
     * <pre>
     * curl localhost:7001/client/12?action=success → Hello!
     * curl localhost:7001/client/12?action=throwFatal → FatalException
     * curl localhost:7001/client/12?action=success → Hello!
     * curl localhost:7001/client/12?action=success → Hello!
     * curl localhost:7001/client/12?action=throwFatal → FatalException
     * curl localhost:7001/client/12?action=success → CircuitBreakerOpenException
     * </pre>
     */
    @CircuitBreaker(requestVolumeThreshold = 4, failureRatio=0.5, delay = 10000, successThreshold = 3)
    public String hello_circuitBreaker(String action) {
        return helloClient.hello(action);
    }


    /**
     * 処理が連続で3回失敗したらサーキットブレーカーを発動したい
     * <pre>
     * curl localhost:7001/client/13?action=throwFatal → FatalException(1 + 2times retry)
     * curl localhost:7001/client/13?action=success → CircuitBreakerOpenException
     * </pre>
     */
    @Retry(maxRetries = 2)
    @CircuitBreaker(successThreshold = 3, requestVolumeThreshold = 3, failureRatio=1.0, delay = 10000)
    public String hello_circuitBreaker_with_retry(String action) {
        return helloClient.hello(action);
    }

    /**
     * 失敗とみなす例外とみなさない例外を指定したい
     * <pre>
     * curl localhost:7001/client/14?action=throwRetryable
     * curl localhost:7001/client/14?action=throwRetryable
     * curl localhost:7001/client/14?action=throwRetryable
     * curl localhost:7001/client/14?action=throwRetryable
     * curl localhost:7001/client/14?action=throwRetryable → RetryableException(not CircuitBreakerOpenException)
     * </pre>
     */
    @CircuitBreaker(successThreshold = 3,
            requestVolumeThreshold = 4,
            failureRatio = 0.5,
            delay = 10000,
            failOn = Exception.class,
            skipOn = SkipException.class)
    public String hello_circuitBreaker_with_skipOn(String action) {
        return helloClient.hello(action);
    }

    /**
     * 同時実行できる数を制限したい。
     * <pre>
     * curl localhost:7001/client/15 &
     * curl localhost:7001/client/15 &
     * curl localhost:7001/client/15 &
     * curl localhost:7001/client/15 & → BulkheadException
     * </pre>
     */
    @Bulkhead(3)
    public String hello_bulkhead() {
        return helloClient.hello("longSleep");
    }

    /**
     * 非同期実行の実行キューを制御したい
     * <pre>
     * curl localhost:7001/client/16
     * </pre>
     */
    @Asynchronous
    @Bulkhead(value = 2, waitingTaskQueue = 1)
    public Future<String> hello_async_with_bulkhead() throws Exception {
        log.info("execute hello_async_with_bulkhead");
        var ret = helloClient.hello("longSleep");
        return CompletableFuture.completedFuture(ret);
    }

    /**
     * 非同期実行でもタイムアウト制御したい
     * <pre>
     * curl localhost:7001/client/17
     * </pre>
     */
    @Asynchronous
    @Timeout(500)
    public CompletionStage<String> hello_async_with_timeout() throws Exception {
        log.info("execute hello_async_with_timeout");
        var ret = helloClient.hello("sleep");
        return CompletableFuture.completedFuture(ret);
    }

    /**
     * 意味のないリトライ制御
     * <pre>
     * curl localhost:7001/client/18
     * </pre>
     */
    @Asynchronous
    @Retry
    public Future<String> hello_async_with_retry() throws Exception {
        log.info("execute hello_async_with_retry");
        CompletableFuture<String> future = new CompletableFuture<>();
        try {
            future.complete(helloClient.hello("throwRetryable"));
        } catch (Exception e) {
            future.completeExceptionally(e);
        }
        return future;
    }

    /**
     * リトライ回数のパラメータを起動時に変更したい
     * <pre>
     * curl localhost:7001/client/19?action=throwRetryable
     * </pre>
     */
    @Retry(maxRetries = 1)
    public String hello_config_override(String action) {
        return helloClient.hello(action);
    }

    @Asynchronous
    @Retry
    public CompletionStage<String> hello_restclinet_async(String action) throws Exception {
        log.info("★");
        return helloClient.asyncHello(action);
    }
}
