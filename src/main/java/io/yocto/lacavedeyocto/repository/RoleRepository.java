package io.yocto.lacavedeyocto.repository;

import io.yocto.lacavedeyocto.domain.Role;

public interface RoleRepository <T extends Role> {
    /* basic crud*/
    T create (T data);
    //    Collection<T> list (int page, int pageSize);
    T get(Long id);
    Boolean delete(Long id);

    /**/
    void addRoleToUser(Long userId, String roleName);
    Role getRoleByUserId(Long userId);
    Role getRoleByUserEmail(String email);
    void updateUserRole(Long userId, String roleName);
}
