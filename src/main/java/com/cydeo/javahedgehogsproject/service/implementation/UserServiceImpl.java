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

        if (securityService.getLoggedInUser().getRole().getDescription().equals("Root User")) {
            return userRepository.findAllByRoleDescriptionOrderByCompany("Admin").stream()
                    .map(user -> mapperUtil.convert(user, new UserDto()))
                    .peek(userDto -> userDto.setIsOnlyAdmin(isOnlyAdmin(userDto)))
                    .collect(Collectors.toList());
        } else {

            Company company = mapperUtil.convert(securityService.getLoggedInCompany(), new Company());

            return userRepository.findAllByCompanyOrderByRole(company).stream()
                    .map(user -> mapperUtil.convert(user, new UserDto()))
                    .peek(userDto -> userDto.setIsOnlyAdmin(isOnlyAdmin(userDto)))
                    .collect(Collectors.toList());
        }

    }

    private Boolean isOnlyAdmin(UserDto userDto) {
        Company company = mapperUtil.convert(userDto.getCompany(), new Company());
        List<User> admins = userRepository.findAllByRoleDescriptionAndCompanyOrderByCompany("Admin", company);
        return userDto.getRole().getDescription().equals("Admin") && admins.size() == 1;
    }

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
