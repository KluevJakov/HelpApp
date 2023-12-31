package com.HelpApp.repository;

import com.HelpApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //поиск пользователя по email
    Optional<User> findByEmail(String email);

    //поиск пациентов
    @Query(value = "select * from users inner join users_roles on users.id = users_roles.user_id where users_roles.roles_id = 1", nativeQuery = true)
    List<User> findUsers();

    //поиск врачей
    @Query(value = "select * from users inner join users_roles on users.id = users_roles.user_id where users_roles.roles_id = 2", nativeQuery = true)
    List<User> findAdmins();
    @Transactional
    @Modifying
    @Query(value = "delete from users_roles where user_id = ?1", nativeQuery = true)
    void removeRoleLinks(Long id);
}
