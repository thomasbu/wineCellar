package io.yocto.lacavedeyocto.service;

import io.yocto.lacavedeyocto.domain.Role;

public interface RoleService {
    Role getRoleByUserId(Long id);
}
