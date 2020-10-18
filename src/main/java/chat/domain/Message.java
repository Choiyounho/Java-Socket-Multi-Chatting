package chat.domain;

import java.util.Objects;

public class Message {

    private final User user;
    private final String description;

    public Message(User user, String description) {
        this.user = user;
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public String getDescription() {
        return description;
    }

    public String getUserName() {
        return user.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(user, message.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }

}
