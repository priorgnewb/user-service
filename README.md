# Spring Boot, Spring Security, JWT, JPA, PostgreSQL, FlywayDB, Rest API

Сервис пользовательских данных. 

Первичная аутентификация выполняется вводом логина и пароля, при успешном входе в систему будет сгенерирован JSON Web Token (JWT) - токен со сроком действия 24 часа. 

Вся информация о пользователях храниться в PostgreSQL.

Реализована интеграция с FlywayDB - добавлены миграции, которые создают необходимые таблицы ролей (admin, user, moderator) и несколько пользователей. Файлы интеграции расположены в `src/main/resources/db/migration`. Для применения миграций нужно раскомментировать строки в конфигурационном файле `application.properties`

Используются Spring Boot, Spring Security, JWT, JPA, PostgreSQL, FlywayDB, Rest API.

## Инструкция по установке

**1. Склонировать репозиторий**

```bash
git clone https://github.com/priorgnewb/user-service.git
```

**2. Создать базу данных PostgreSQL**

```bash
create database taskdb
```
- run `src/main/resources/taskdb.sql`

**3. Указать имя и пароль пользователя PostgreSQL**

+ открыть `src/main/resources/application.properties`
+ изменить `spring.datasource.username` и `spring.datasource.password` на значения, используемые вами в PostgreSQL

**4. Запустите приложение, используя Maven**

```bash
mvn spring-boot:run
```
Приложение доступно по URL <http://localhost:8080>

## Тестирование Rest APIs

Сервис предоставляет следующие CRUD APIs:

### Аутентификация

| Метод | URL | Описание | Валидный шаблон для запроса | 
| ------ | --- | ---------- | --------------------------- |
| POST   | /api/auth/signup | Зарегистрироваться | [JSON](#signup) |
| POST   | /api/auth/signin | Войти | [JSON](#signin) |

### Действия над пользователями

| Метод | URL | Описание | Валидный шаблон для запроса | 
| ------ | --- | ----------- | ------------------------- |
| GET    | /api/users/my | Показать профиль текущего пользователя | |
| GET    | /api/users/{username}/profile | Показать профиль пользователя с указанным username | |
| POST   | /api/users | Создать пользователя (для администраторов) | [JSON](#usercreate) |
| PUT    | /api/users/{username} | Обновить профиль пользователя (для пользователя, вошедшего в систему, и для администраторов) | [JSON](#userupdate) |
| DELETE | /api/users/{username} | Удалить профиль пользователя (для пользователя, вошедшего в систему, и для администраторов) | |
| PUT    | /api/users/{username}/giveAdmin | Установить пользователю роль администратора (для администраторов) | |
| PUT    | /api/users/{username}/takeAdmin | Отозвать у пользователя роль администратора (для администраторов) | |

### Служебные действия (для администраторов)

| Метод | URL | Описание |
| ------ | --- | ----------- |
| GET    | /api/admin/allusers | Показать список всех пользователей |
| GET    | /api/admin/roles | Показать список доступных для назначения ролей |
| GET    | /api/admin/alladmins | Показать список администраторов сервиса |
| GET    | /api/admin/alladminsid | Показать список ID администраторов сервиса |


Тестирование API возможно выполнять в Postman или в другом REST-клиенте.

## Валидные шаблоны запросов в формате JSON

##### <a id="signup">Зарегистрироваться -> /api/auth/signup</a>
```json
{
  "username": "Aleksey",
  "email": "alex@mail.org",
  "password": "alExDevve"
}
```

##### <a id="signin">Войти -> /api/auth/signin</a>
```json
{
  "username": "Aleksey",
  "password": "alExDevve"
}
```

##### <a id="usercreate">Создать пользователя -> /api/users</a>
```json
{
    "username": "egorlk",
    "email": "legore@testov.org",
    "password":"Pas1E!goR"
}
```

##### <a id="userupdate">Обновить профиль пользователя (пароль) -> /api/users/{username}</a>
```json
{
  "username": "Aleksey",
  "email": "alex@mail.org",
  "password": "alExDevveNeWpAs!woRd"
}
```
