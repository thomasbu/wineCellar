package io.yocto.lacavedeyocto.service.implementation;

import io.yocto.lacavedeyocto.domain.Role;
import io.yocto.lacavedeyocto.repository.RoleRepository;
import io.yocto.lacavedeyocto.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository<Role> roleRepository;
    @Override
    public Role getRoleByUserId(Long id) {
        return roleRepository.getRoleByUserId(id);
    }

    @Override
    public Collection<Role> getRoles() {
        return roleRepository.list();
    }
}
