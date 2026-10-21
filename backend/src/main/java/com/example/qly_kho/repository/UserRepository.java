package com.example.qly_kho.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.qly_kho.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User>{

    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndDeletedAtIsNull(String username);

    Optional<User> findByEmail(String email);

    Optional<Long> findIdByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @Query("""
        SELECT u 
        FROM User u
        LEFT JOIN FETCH u.roles r
        LEFT JOIN FETCH r.permissions
        WHERE u.username = :username
    """)
    Optional<User> findByUsernameWithRolesAndPermissions(@Param("username") String username);


    @Query("""
        SELECT u.id
        FROM User u
        JOIN u.roles r
        WHERE r.id = :roleId
    """)
    Set<Long> findUserIdByRoleId(@Param("roleId") Long roleId);

    @Modifying
    @Query("""
        UPDATE User u 
        SET u.permissionVersion = u.permissionVersion + 1 
        WHERE u.id IN :userIds
    """)
    void incrementPermissionVersionByUserIds(Set<Long> userIds);
}
