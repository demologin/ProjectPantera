package com.javarush.zyibin.service;

import com.javarush.zyibin.model.Role;
import com.javarush.zyibin.model.User;
import com.javarush.zyibin.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminUserServiceTest {

    @Mock
    private UserRepository userRepository;

    private AdminUserService adminUserService;

    @BeforeEach
    void setUp() {
        adminUserService = new AdminUserService(userRepository);
    }

    @Test
    void shouldChangeUserRole_whenAdminChangesUserRoleOfOtherUser() {
        long adminId = 1L;
        long targetUserId = 2L;
        Role newRole = Role.USER;
        
        User targetUser = new User(targetUserId, "targetuser", "hashedpass", "target@example.com", Role.USER);
        
        when(userRepository.findById(targetUserId)).thenReturn(Optional.of(targetUser));
        
        adminUserService.changeUserRole(adminId, targetUserId, newRole);
        
        assertEquals(newRole, targetUser.getRole());
        verify(userRepository).findById(targetUserId);
    }

    @Test
    void shouldNotChangeRole_whenAdminAttemptsToChangeOwnRole() {
        long adminId = 1L;
        Role newRole = Role.USER;
        
        User adminUser = new User(adminId, "admin", "hashedpass", "admin@example.com", Role.ADMIN);
        
        when(userRepository.findById(adminId)).thenReturn(Optional.of(adminUser));
        
        adminUserService.changeUserRole(adminId, adminId, newRole);
        
        assertEquals(Role.ADMIN, adminUser.getRole());
        verify(userRepository).findById(adminId);
    }

    @Test
    void shouldNotThrowException_whenTargetUserNotFound() {
        long adminId = 1L;
        long nonExistentUserId = 999L;
        Role newRole = Role.USER;
        
        when(userRepository.findById(nonExistentUserId)).thenReturn(Optional.empty());
        
        assertDoesNotThrow(() -> adminUserService.changeUserRole(adminId, nonExistentUserId, newRole));
        
        verify(userRepository).findById(nonExistentUserId);
    }

    @Test
    void shouldChangeToAdminRole_whenRequested() {
        long adminId = 1L;
        long targetUserId = 2L;
        Role newRole = Role.ADMIN;
        
        User targetUser = new User(targetUserId, "targetuser", "hashedpass", "target@example.com", Role.USER);
        targetUser.changeRole(Role.USER);
        
        when(userRepository.findById(targetUserId)).thenReturn(Optional.of(targetUser));
        
        adminUserService.changeUserRole(adminId, targetUserId, newRole);
        
        assertEquals(Role.ADMIN, targetUser.getRole());
        verify(userRepository).findById(targetUserId);
    }

    @Test
    void shouldChangeToUserRole_whenRequested() {
        long adminId = 1L;
        long targetUserId = 2L;
        Role newRole = Role.USER;
        
        User targetUser = new User(targetUserId, "targetuser", "hashedpass", "target@example.com", Role.USER);
        targetUser.changeRole(Role.USER);
        
        when(userRepository.findById(targetUserId)).thenReturn(Optional.of(targetUser));
        
        adminUserService.changeUserRole(adminId, targetUserId, newRole);
        
        assertEquals(Role.USER, targetUser.getRole());
        verify(userRepository).findById(targetUserId);
    }

    @Test
    void shouldChangeToUserRole_whenRequestedAgain() {
        long adminId = 1L;
        long targetUserId = 2L;
        Role newRole = Role.USER;
        
        User targetUser = new User(targetUserId, "targetuser", "hashedpass", "target@example.com", Role.USER);
        targetUser.changeRole(Role.USER);
        
        when(userRepository.findById(targetUserId)).thenReturn(Optional.of(targetUser));
        
        adminUserService.changeUserRole(adminId, targetUserId, newRole);
        
        assertEquals(Role.USER, targetUser.getRole());
        verify(userRepository).findById(targetUserId);
    }

    @Test
    void shouldHandleMultipleRoleChanges() {
        long adminId = 1L;
        long targetUserId = 2L;
        
        User targetUser = new User(targetUserId, "targetuser", "hashedpass", "target@example.com", Role.USER);
        
        when(userRepository.findById(targetUserId)).thenReturn(Optional.of(targetUser));
        
        assertEquals(Role.USER, targetUser.getRole());
        
        adminUserService.changeUserRole(adminId, targetUserId, Role.USER);
        assertEquals(Role.USER, targetUser.getRole());
        
        adminUserService.changeUserRole(adminId, targetUserId, Role.ADMIN);
        assertEquals(Role.ADMIN, targetUser.getRole());
        
        adminUserService.changeUserRole(adminId, targetUserId, Role.USER);
        assertEquals(Role.USER, targetUser.getRole());
        
        verify(userRepository, times(3)).findById(targetUserId);
    }

    @Test
    void shouldPreserveOtherUserProperties_whenChangingRole() {
        long adminId = 1L;
        long targetUserId = 2L;
        Role newRole = Role.USER;
        
        User targetUser = new User(targetUserId, "targetuser", "hashedpass", "target@example.com", Role.USER);
        targetUser.changeRole(Role.USER);
        
        when(userRepository.findById(targetUserId)).thenReturn(Optional.of(targetUser));
        
        String originalUsername = targetUser.getUsername();
        String originalEmail = targetUser.getEmail();
        String originalPasswordHash = targetUser.getPasswordHash();
        
        adminUserService.changeUserRole(adminId, targetUserId, newRole);
        
        assertEquals(originalUsername, targetUser.getUsername());
        assertEquals(originalEmail, targetUser.getEmail());
        assertEquals(originalPasswordHash, targetUser.getPasswordHash());
        assertEquals(newRole, targetUser.getRole());
        
        verify(userRepository).findById(targetUserId);
    }
}
