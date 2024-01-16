package com.tps.jurados.persistence;

import com.tps.jurados.domain.dto.AuthCheckTokenDto;
import com.tps.jurados.domain.repository.IUserRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository implements IUserRepository {

    private static final String TOKEN_SINGLE = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VybmFtZSIsInJvbGUiOiJhZG1pbiIsImVtdWxhdGVkSWQiOjAsImV4cCI6MTcwMTQ2OTc2MywidXNlcklkIjoxLCJpYXQiOjE3MDE0NjYxNjMsInVzZXJuYW1lIjoidXNlcm5hbWUifQ.cwUWIqcvb1fCAphDmrwyXbO1rU2JNuM53gnpRGe0efXSpXgkybCQ6yFBWA1ClQYLL_n1CQ501LpAqMYvgAppPA";
    private static final String USERNAME = "admin";

    @Override
    public List<AuthCheckTokenDto> getAuthCheckToken(String username) {
        List<AuthCheckTokenDto> list = new ArrayList<>();
        list.add(new AuthCheckTokenDto(USERNAME, TOKEN_SINGLE,true,"person-list:get"));
        list.add(new AuthCheckTokenDto(USERNAME, TOKEN_SINGLE,true, "person-id:get"));
        list.add(new AuthCheckTokenDto(USERNAME,TOKEN_SINGLE,true, "expenses-id:get"));

        return list;
    }
}
