package io.yocto.lacavedeyocto.repository;

import io.yocto.lacavedeyocto.domain.User;

import java.util.Collection;

public interface UserRepository<T extends User> {
    /* basic crud*/
    T create (T data);
//    Collection<T> list (int page, int pageSize);
    T get(Long id);
    Boolean delete(Long id);

    /**/
    User getUserByEmail(String email);
}
