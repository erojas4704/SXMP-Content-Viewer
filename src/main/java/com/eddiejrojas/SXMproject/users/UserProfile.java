package com.eddiejrojas.SXMproject.users;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides a layer of user data that we can transmit, without sending sensitive user data.
 */
public class UserProfile {
    public String username;
    public String avatarURL;

    UserProfile(User user) {
        this.username = user.getUsername();
        this.avatarURL = user.getAvatarURL();
    }

    public static UserProfile from(User user) {
        UserProfile userProfile = new UserProfile(user);
        return userProfile;
    }

    public static List<UserProfile> from(List<User> users) {
        List<UserProfile> profiles = new ArrayList<>();
        for (User u : users) {
            profiles.add(UserProfile.from(u));
        }
        return profiles;
    }
}
