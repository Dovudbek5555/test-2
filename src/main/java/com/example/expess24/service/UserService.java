package com.example.expess24.service;

import com.example.expess24.entity.*;
import com.example.expess24.entity.enums.Gender;
import com.example.expess24.entity.enums.RoleEnum;
import com.example.expess24.entity.exception.GenericException;
import com.example.expess24.payload.ApiResponse;
import com.example.expess24.payload.AuthLoginDto;
import com.example.expess24.payload.UserDto;
import com.example.expess24.repository.*;
import com.example.expess24.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ContactRepository contactRepository;
    private final CardRepository cardRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final BasketRepository basketRepository;

    public ApiResponse addUser(UserDto userDto) {
        if (userRepository.existsByPhoneNumber(userDto.getPhoneNumber())) {
            return new ApiResponse("This phone number is already registered", false);
        }

        List<Contact> contacts = contactRepository.findAllById(userDto.getContactIds());
        List<Card> cards = cardRepository.findAllById(userDto.getCardIds());

        User user = User.builder()
                .phoneNumber(userDto.getPhoneNumber())
                .password(userDto.getPassword())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .birthDate(userDto.getBirthDate())
                .roleEnum(RoleEnum.values()[userDto.getRoleId()])
                .gender(userDto.getGender())
                .bonusAccount(userDto.getBonusAccount())
                .contacts(contacts)
                .cards(cards)
                .build();

        userRepository.save(user);

        Basket basket = Basket.builder().owner(user).preBasket(new ArrayList<>()).build();
        basketRepository.save(basket);

        return new ApiResponse("User successfully registered", true);
    }

    public UserDto getOneUser(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> GenericException.builder().message("User not found").statusCode(400).build());

        return UserDto.builder()
                .id(user.getId())
                .phoneNumber(user.getPhoneNumber())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthDate(user.getBirthDate())
                .roleId(user.getRoleEnum().ordinal())
                .gender(user.getGender())
                .bonusAccount(user.getBonusAccount())
                .contactIds(user.getContacts().stream().map(Contact::getId).toList())
                .cardIds(user.getCards().stream().map(Card::getId).toList())
                .build();
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();

        for (User user : users) {
            UserDto userDto = UserDto.builder()
                    .id(user.getId())
                    .phoneNumber(user.getPhoneNumber())
                    .password(user.getPassword())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .birthDate(user.getBirthDate())
                    .roleId(user.getRoleEnum().ordinal())
                    .gender(user.getGender())
                    .bonusAccount(user.getBonusAccount())
                    .contactIds(user.getContacts().stream().map(Contact::getId).toList())
                    .cardIds(user.getCards().stream().map(Card::getId).toList())
                    .build();
            userDtos.add(userDto);
        }

        return userDtos;
    }

    public ApiResponse updateUser(UserDto userDto) {
        User user = userRepository.findById(userDto.getId())
                .orElseThrow(() -> GenericException.builder().message("User not found").statusCode(400).build());

        List<Contact> contacts = contactRepository.findAllById(userDto.getContactIds());
        List<Card> cards = cardRepository.findAllById(userDto.getCardIds());

        if (userRepository.existsByPhoneNumberAndIdNot(userDto.getPhoneNumber(), user.getId())) {

            user.setPhoneNumber(userDto.getPhoneNumber());
            user.setPassword(userDto.getPassword());
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setBirthDate(userDto.getBirthDate());
            user.setRoleEnum(RoleEnum.values()[userDto.getRoleId()]);
            user.setGender(userDto.getGender());
            user.setBonusAccount(userDto.getBonusAccount());
            user.setContacts(contacts);
            user.setCards(cards);

            userRepository.save(user);
            return new ApiResponse("User successfully updated", true);
        }
        return new ApiResponse("This phone number already exist", false);
    }

    public ApiResponse deleteUser(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> GenericException.builder().message("User not found").statusCode(400).build());

        userRepository.delete(user);
        return new ApiResponse("User successfully deleted", true);
    }

    public ApiResponse signUp(String phoneNumber, String firstName, String password){
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            return new ApiResponse("This phone number is already registered", false);
        }

        User user = User.builder().phoneNumber(phoneNumber).firstName(firstName).password(passwordEncoder.encode(password)).roleEnum(RoleEnum.ROLE_CLIENT).build();
        userRepository.save(user);

        Basket basket = Basket.builder().owner(user).preBasket(new ArrayList<>()).build();
        basketRepository.save(basket);

        return new ApiResponse("Succesfully registered", true);
    }

    public ApiResponse forLogin(AuthLoginDto authLoginDto) {
        User user = userRepository.findByPhoneNumber(authLoginDto.getPhoneNumber())
                .orElseThrow(() -> GenericException.builder().message("User not found").statusCode(404).build());

        if (passwordEncoder.matches(authLoginDto.getPassword(), user.getPassword())) {
            String token = jwtProvider.generateToken(authLoginDto.getPhoneNumber());
            return new ApiResponse(token, true);
        }
        return new ApiResponse("Password does not match", false);
    }

}
