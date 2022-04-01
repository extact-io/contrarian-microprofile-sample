package io.extact.mp.sample.config;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class CdiSampleBean {
    // Field InjectionによるConfigインスタンスの取得
    @Inject
    private Config fieldConfig;

    // Field Injectionによる設定項目ごとの取得
    @Inject
    @ConfigProperty(name = "name")
    private String name;
    // Field Injectionによる設定項目ごとの取得（型変換あり）
    @Inject
    @ConfigProperty(name = "age")
    private int age;
    // Field Injectionによる設定項目ごとの取得（デフォルト値設定あり）
    @Inject
    @ConfigProperty(name = "address", defaultValue = "empty")
    private String address;
    // Field Injectionによる設定項目ごとの取得（キー名区切りあり）
    @Inject
    @ConfigProperty(name = "foo.bar.baz")
    private String baz;
    // Field Injectionによる設定項目ごとの取得（配列変換あり）
    @Inject
    @ConfigProperty(name = "values")
    private String[] arrayValues;
    // Field Injectionによる設定項目ごとの取得（List変換あり）
    @Inject
    @ConfigProperty(name = "values")
    private List<String> listValues;
    // Field Injectionによる設定項目ごとの取得（Set変換あり）
    @Inject
    @ConfigProperty(name = "values")
    private Set<String> setValues;

    // Constructor InjectionによるConfigインスタンスの取得
    @Inject
    public CdiSampleBean(Config config) {
        System.out.println("**** ConstructorInjectionConfig ****");
        dumpConfigInstance(config);
    }


    // ------------------------------------------------------ dump methods

    public void dumpFieldInjectionConfig() {
        System.out.println("**** FieldInjectionConfig ****");
        dumpConfigInstance(this.fieldConfig);
    }
    public void dumpConfigProperties() {
        System.out.println("**** ConfigProperties ****");
        System.out.println("name:" + this.name);
        System.out.println("age:" + this.age);
        System.out.println("address:" + this.address);
        System.out.println("foo.bar.baz:" + this.baz);
        System.out.println("arrayValues:" + String.join(":", this.arrayValues));
        System.out.println("arrayValues:" + this.listValues.stream().collect(Collectors.joining("-")));
        System.out.println("setValues:" + this.setValues.stream().collect(Collectors.joining("/")));
        System.out.println("");
    }
    public void dumpConfigInstance(Config config) {
        String name = config.getValue("name", String.class);
        System.out.println("name:" + name);
        int age = config.getValue("age", int.class);
        System.out.println("age:" + age);
        Optional<String> address = config.getOptionalValue("address", String.class);
        System.out.println("address:" + address.orElse("empty"));
        String baz = config.getValue("foo.bar.baz", String.class);
        System.out.println("foo.bar.baz:" + baz);
        String[] values = config.getValue("values", String[].class);
        System.out.println("values:" + String.join(":", values));
        System.out.println("");
    }
}
