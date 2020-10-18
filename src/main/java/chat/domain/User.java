package chat.domain;

import java.util.Objects;

public class User {

    private String name;

    public User(String name) {
        isBlank(name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private void isBlank(String name) {
        if (name == null || name.trim().length() == 0) {
            throw new IllegalArgumentException("이름을 입력해주세요.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
