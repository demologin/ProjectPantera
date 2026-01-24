package com.javarush.zyibin.service;

import java.util.List;

public class AvatarService {
    public List<String> getAvailableAvatars() {
        return List.of(
                "/resources/avatars/default/avatar1.png",
                "/resources/avatars/default/avatar2.png",
                "/resources/avatars/default/avatar3.png",
                "/resources/avatars/default/avatar4.png",
                "/resources/avatars/default/avatar5.png",
                "/resources/avatars/default/avatar6.png"
        );
    }
}
