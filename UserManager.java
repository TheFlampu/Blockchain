package blockchain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserManager {
    private static UserManager instance;

    private final List<User> users = new ArrayList<>();

    private UserManager() {
        try {
            users.add(new User("Коля"));
            users.add(new User("Влад"));
        } catch (Exception ignored) {
        }
    }

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public User getRandomUser() {
        return users.get(new Random().nextInt(users.size()));
    }
}
