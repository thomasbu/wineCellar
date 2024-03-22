package io.yocto.lacavedeyocto.service;

import io.yocto.lacavedeyocto.domain.Role;

import java.util.Collection;

public interface RoleService {
    Role getRoleByUserId(Long id);

    Collection<Role> getRoles();
}
