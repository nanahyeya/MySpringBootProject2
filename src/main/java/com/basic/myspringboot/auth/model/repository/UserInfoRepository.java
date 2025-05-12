package com.basic.myspringboot.auth.model.repository;

public interface UserInfoRepository extends ListCrudRepository<UserInfo, Integer> {

Optional<UserInfo> findByEmail(String email);

}