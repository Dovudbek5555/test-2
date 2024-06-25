package com.example.expess24.component;

import com.example.expess24.entity.Role;
import com.example.expess24.entity.enums.RoleEnum;
import com.example.expess24.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count()==0){
            Role client = new Role(RoleEnum.ROLE_CLIENT);
            Role restaurant = new Role(RoleEnum.ROLE_RESTAURANT);
            Role admin = new Role(RoleEnum.ROLE_ADMIN);
            roleRepository.save(client);
            roleRepository.save(restaurant);
            roleRepository.save(admin);
        }
    }

}
