package com.cydeo.javahedgehogsproject.service.implementation;

import com.cydeo.javahedgehogsproject.dto.CompanyDto;
import com.cydeo.javahedgehogsproject.dto.RoleDto;
import com.cydeo.javahedgehogsproject.dto.UserDto;
import com.cydeo.javahedgehogsproject.entity.Company;
import com.cydeo.javahedgehogsproject.entity.User;
import com.cydeo.javahedgehogsproject.mapper.MapperUtil;
import com.cydeo.javahedgehogsproject.repository.UserRepository;
import com.cydeo.javahedgehogsproject.service.CompanyService;
import com.cydeo.javahedgehogsproject.service.RoleService;
import com.cydeo.javahedgehogsproject.service.SecurityService;
import com.cydeo.javahedgehogsproject.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MapperUtil mapperUtil;
    private final RoleService roleService;
    private final CompanyService companyService;
    private final SecurityService securityService;


    public UserServiceImpl(UserRepository userRepository, MapperUtil mapperUtil, RoleService roleService, CompanyService companyService, SecurityService securityService) {
        this.userRepository = userRepository;
        this.mapperUtil = mapperUtil;
        this.roleService = roleService;
        this.companyService = companyService;
        this.securityService = securityService;
    }

    @Override
    public UserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return mapperUtil.convert(user, new UserDto());
    }

    @Override
    public UserDto findById(long id) {
        User user = userRepository.findById(id).get();
        return mapperUtil.convert(user, new UserDto());
    }

    @Override
    public List<UserDto> findAll() {
        UserDto user = securityService.getLoggedInUser();
        Company loggedInCompany = mapperUtil.convert(securityService.getLoggedInCompany(), new Company());
        List<User> all = userRepository.findAllByCompanyIsOrderByCompanyAscRoleAsc(loggedInCompany);

        if (user.getRole().getDescription().equals("Root User")) {
            return all.stream().filter(user1 -> user1.getRole().getDescription().equals("Admin"))
                    .map(user1 -> mapperUtil.convert(user1, new UserDto()))
                    .collect(Collectors.toList());
        } else {
            return all.stream().map(user1 -> mapperUtil.convert(user, new UserDto())).collect(Collectors.toList());

        }
    }

//    @Override
//    public List<UserDto> findAll() {
//        UserDto loggedInUserDto = securityService.getLoggedInUser();
//        if (loggedInUserDto.getRole().getId() == 1L || loggedInUserDto.getRole().getId() == 2L) {
//            loggedInUserDto.setIsOnlyAdmin(true);
//        }
//        Company loggedInCompany = mapperUtil.convert(securityService.getLoggedInCompany(), new Company());
//        return userRepository.findAllByCompany_IdIs(loggedInCompany.getId()).stream()
//                .map(user -> mapperUtil.convert(user, new UserDto()))
//                .collect(Collectors.toList());
//    }

    @Override
    public List<UserDto> findAllSortedByCompanyAndRoles() {

        return null;
    }

    @Override
    public void save(UserDto user) {

        User obj = mapperUtil.convert(user, new User());
        obj.setEnabled(true);
        userRepository.save(obj);

    }

}
