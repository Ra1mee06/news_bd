package app.service;

import app.dto.RegisterRequest;
import app.dto.UserResponse;
import app.entity.Role;
import app.entity.User;
import app.exception.BadRequestException;
import app.exception.ResourceNotFoundException;
import app.mapper.EntityMapper;
import app.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Регистрация нового пользователя с указанной ролью.
     */
    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Пользователь с таким username уже существует");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setParentName(request.getParentName());
        user.setRole(request.getRole());
        return EntityMapper.toUserResponse(userRepository.save(user));
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден: " + id));
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден: " + username));
    }

    public List<UserResponse> findAll() {
        return userRepository.findAll().stream()
                .map(EntityMapper::toUserResponse)
                .toList();
    }

    public UserResponse update(Long id, RegisterRequest request) {
        User user = getById(id);
        if (!user.getUsername().equals(request.getUsername()) && userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Пользователь с таким username уже существует");
        }
        user.setUsername(request.getUsername());
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setParentName(request.getParentName());
        user.setRole(request.getRole());
        return EntityMapper.toUserResponse(userRepository.save(user));
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Пользователь не найден: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден: " + username));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
    }

    public static boolean isAdmin(User user) {
        return user.getRole() == Role.ADMIN;
    }

    public static boolean isReporter(User user) {
        return user.getRole() == Role.REPORTER;
    }

    public static boolean isSubscriber(User user) {
        return user.getRole() == Role.SUBSCRIBER;
    }
}
