package org.tyaa.demo.java.spring.springbootreactjs.junit.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.tyaa.demo.java.spring.springbootreactjs.entities.Role;
import org.tyaa.demo.java.spring.springbootreactjs.models.ResponseModel;
import org.tyaa.demo.java.spring.springbootreactjs.models.RoleModel;
import org.tyaa.demo.java.spring.springbootreactjs.repositories.RoleHibernateDAO;
import org.tyaa.demo.java.spring.springbootreactjs.repositories.UserHibernateDAO;
import org.tyaa.demo.java.spring.springbootreactjs.services.AuthService;
import org.tyaa.demo.java.spring.springbootreactjs.services.interfaces.IAuthService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AuthServiceTest {
    // Внедрение экземпляра UserHibernateDAO
    // для дальнейшего использования службой AuthService
    @Mock
    private UserHibernateDAO userDAO;
    @Mock
    private RoleHibernateDAO roleDAO;
    @Mock
    public PasswordEncoder passwordEncoder;
    // Интерфейсный макет тестируемого класса
    @Mock
    private IAuthService authServiceMock;
    // Внедрение экземпляра AuthService для его дальнейшего тестирования -
    // так, что при создании в него внедрятся все необходимые зависимости,
    // помеченные в классе тестов аннтацией @Mock
    @InjectMocks
    private AuthService authService;
    // Заглушка на основе класса сущности Role
    ArgumentCaptor<Role> roleArgument =
            ArgumentCaptor.forClass(Role.class);
    ArgumentCaptor<Long> roleIdArgument =
            ArgumentCaptor.forClass(Long.class);
    // Тест-кейс, который:
    // 1. обучает интерфейсный макет службы - какой объект должен возвращать ее метод getAllRoles;
    // 2. вызывает тестируемый метод;
    // 3. проверяет, есть ли объект результата, есть ли в нем данные,
    // ожидаемая ли размерность списка данных получилась
    // (создается тестровщиком на основе интерфейса еще не реализованной службы -
    // как задание для разработчика реализовать методы службы в конкретном классе)
    @Test
    void shouldGetAllRolesReturnSuccessfully () {
        // Обучаем макет:
        // вернуть что? - результат, равный ...
        doReturn(
                ResponseModel.builder()
                        .status(ResponseModel.SUCCESS_STATUS)
                        .data(Arrays.asList(new RoleModel(1L, "ROLE_DEMO1"),
                                new RoleModel(2L, "ROLE_DEMO2"),
                                new RoleModel(3L, "ROLE_DEMO3")))
                        .build()
        ).when(authServiceMock) // откуда? - из объекта authServiceMock
            .getAllRoles(); // как результат вызова какого метода? - getAllRoles
        // вызов настроенного выше метода макета, полученного из интерфейса
        ResponseModel responseModel =
                authServiceMock.getAllRoles();
        // проверки возвращенного результата
        assertNotNull(responseModel);
        assertNotNull(responseModel.getData());
        assertEquals(((List)responseModel.getData()).size(), 3);
    }
    // Тест-кейс, который:
    // 1. создает объект модели, который должен передаваться в метод createRole
    // 2. вызывает тестируемый метод
    // 3. проверяет, было ли возвращено значение,
    // есть ли статус успешного выполнения в модели результата,
    // был ли вызван каскадно метод ... при выполнении тестируемого метода,
    // причем толко один раз
    // (создается разработчиком, когда тестируемый класс службы уже реализован)
    @Test
    void shouldCreateRoleReturnSuccessfully() {
        final RoleModel roleModel =
                RoleModel.builder()
                        .name("ROLE_DEMO")
                        .build();
        ResponseModel responseModel =
                authService.createRole(roleModel);
        // Проверка, что результат не равен null
        assertNotNull(responseModel);
        // Проверка, что результат содержит положительный статус-код
        assertEquals(ResponseModel.SUCCESS_STATUS, responseModel.getStatus());
        // Проверка, что в результате вызванного выше метода (createRole)
        // минимум 1 раз был вызван метод save у внедренного объекта RoleHibernateDAO
        // (roleArgument.capture() - заглушка, символизирующая,
        // что в метод save был передан какой-то аргумент подходящего типа)
        verify(roleDAO, atLeast(1))
                .save(roleArgument.capture());
    }
    @Test
    void shouldGetRoleUsersSuccessfully() {
        doReturn(
                ResponseModel.builder()
                        .status(ResponseModel.SUCCESS_STATUS)
                        .data(Arrays.asList(
                                new RoleModel(1L, "ROLE_DEMO1"),
                                new RoleModel(2L, "ROLE_DEMO2"),
                                new RoleModel(3L, "ROLE_DEMO3")))
                        .build()
        ).when(authServiceMock)
                .getRoleUsers(1L);
        ResponseModel responseModel =
                authService.getRoleUsers(1L);
        assertNotNull(responseModel);
        // assertEquals(ResponseModel.SUCCESS_STATUS, responseModel.getStatus());  // how to create or
        verify(roleDAO, atLeast(1))
                .findById(roleIdArgument.capture()); //

    }
}
