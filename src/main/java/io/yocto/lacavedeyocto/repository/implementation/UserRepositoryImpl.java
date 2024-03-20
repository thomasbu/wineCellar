package io.yocto.lacavedeyocto.repository.implementation;

import io.yocto.lacavedeyocto.domain.Role;
import io.yocto.lacavedeyocto.domain.User;
import io.yocto.lacavedeyocto.exception.ApiException;
import io.yocto.lacavedeyocto.repository.RoleRepository;
import io.yocto.lacavedeyocto.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.UUID;

import static io.yocto.lacavedeyocto.enumeration.RoleType.ROLE_USER;
import static io.yocto.lacavedeyocto.enumeration.VerificationType.ACCOUNT;
import static io.yocto.lacavedeyocto.query.UserQuery.*;
import static java.util.Map.of;
import static java.util.Objects.requireNonNull;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository<User> {


    private final NamedParameterJdbcTemplate jdbc;
    private final RoleRepository<Role> roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public User create(User user) {
        if(getCountEmail(user.getEmail().trim().toLowerCase()) > 0) throw new ApiException("Email already in use. Use a different email and try again");
        try {
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource parameters = getSqlParameterSource(user);
            jdbc.update(INSERT_USER_QUERY, parameters, holder);
            user.setId(requireNonNull(holder.getKey()).longValue());
            roleRepository.addRoleToUser(user.getId(), ROLE_USER.name());
            String verificationUrl = getVerificationUrl(UUID.randomUUID().toString(), ACCOUNT.getType());
            jdbc.update(INSERT_ACCOUNT_VERIFICATION_QUERY, of("userId", user.getId(), "url", verificationUrl));
//            emailService.sendVerificationUrl(user.getFirstName(), user.getEmail(), verificationUrl, ACCOUNT);
            user.setEnabled(false);
            user.setNotLocked(true);
            return user;
        }  catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public User get(Long id) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    private Integer getCountEmail(String email) {
        return jdbc.queryForObject(COUNT_USER_EMAIL_QUERY, of("email", email), Integer.class);
    }

    private SqlParameterSource getSqlParameterSource(User user) {
        return new MapSqlParameterSource()
                .addValue("firstName", user.getFirstName())
                .addValue("lastName", user.getFirstName())
                .addValue("email", user.getEmail())
                .addValue("password", bCryptPasswordEncoder.encode(user.getPassword()));
    }

    private String getVerificationUrl(String key, String type) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/verify" + type + "/" + key).toUriString();
    }
}
