package sample;

import java.util.List;

public record User(String id, String password, String name, List<String> groups) {
}
