package com.fundamentos.springboot.fundamentos.repository;

import com.fundamentos.springboot.fundamentos.dto.UserDto;
import com.fundamentos.springboot.fundamentos.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    @Query("Select u from User u where u.email=?1")
    Optional<User> findByUserEmail(String email);

    @Query("SELECT u FROM User u WHERE u.name LIKE ?1%")
    List<User> findAndSort(String name, Sort sort);

    List<User> findByName(String name);

    Optional<User> findByNameAndEmail(String name, String email);

    List<User> findByNameLike(String name);

    List<User> findByNameOrEmail(String name, String email);

    List<User> findByBirtDateBetween(LocalDate begging, LocalDate end);

    List<User> findByNameLikeOrderByIdDesc(String Name);

    List<User> findAll();

    @Query("SELECT new com.fundamentos.springboot.fundamentos.dto.UserDto(u.id, u.name, u.birtDate)" +
            "FROM User u " +
            "WHERE u.birtDate=:dateParam " +
            "AND u.email=:emailParam")
    Optional<UserDto> getAllByBirtDateAndEmail(@Param("dateParam") LocalDate date, @Param("emailParam") String email);

}
