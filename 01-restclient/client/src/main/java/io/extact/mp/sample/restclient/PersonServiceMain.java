package io.extact.mp.sample.restclient;

import java.util.Map;
import java.util.stream.Collectors;

import javax.enterprise.inject.spi.CDI;

import org.slf4j.bridge.SLF4JBridgeHandler;

import io.extact.mp.sample.restclient.jaxrs.JaxrsPersonService;
import io.extact.mp.sample.restclient.mp.RestClientPersonService;

public class PersonServiceMain {

    private static final Map<String, Class<? extends PersonService>> BEANS_MAP = Map.of(
            "jaxrs", JaxrsPersonService.class,
            "restclient", RestClientPersonService.class);

    public static void main(String[] args) {
        // java.util.loggingの出力をSLF4Jへdelegate
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        // Helidonの起動
        io.helidon.microprofile.cdi.Main.main(args);

        // 準備処理
        var targetBeanClass = BEANS_MAP.getOrDefault(
                args.length != 0
                        ? args[0].toLowerCase()
                        : "",
                RestClientPersonService.class);
        System.out.println("実行CDI => " + targetBeanClass.getSimpleName());
        var personClient = CDI.current().select(targetBeanClass).get();
        personClient.reset();

        // リクエスト実行
        var personByGet = personClient.getPerson(1);
        var personByAdd = personClient.addPerson(new Person(null, "toshio", 30));
        var personByFind = personClient.findPerson("t");

        // 結果表示
        System.out.println(personByGet);
        System.out.println(personByAdd);
        System.out.println(personByFind.stream().map(Object::toString).collect(Collectors.joining("/")));

        // 例外ハンドリング
        try {
            personClient.addPerson(new Person(null, "toshio", 30)); // 既に登録済みのため名前重複エラー
        } catch (PersonClientException e) {
            System.out.println("occur exception! type:" + e.getClientError());
        }

        // サーバスレッドが残るためプロセスを終了させる
        System.exit(0);
    }
}
