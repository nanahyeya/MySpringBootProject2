package com.basic.myspringboot.auth.config;

import org.springframework.security.core.userdetails.UserDetails;

public class UserInfoUserDetails implements UserDetails {
private String email;
private String password;
private List<GrantedAuthority> authorities;

public UserInfoUserDetails(UserInfo userInfo) {
email=userInfo.getEmail();
password=userInfo.getPassword();
authorities= Arrays.stream(userInfo.getRoles().split(","))

.map(SimpleGrantedAuthority::new)
.collect(Collectors.toList());

}
}