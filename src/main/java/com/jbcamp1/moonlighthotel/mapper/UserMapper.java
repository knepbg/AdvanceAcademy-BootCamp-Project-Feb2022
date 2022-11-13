package com.jbcamp1.moonlighthotel.mapper;

import com.jbcamp1.moonlighthotel.dto.user.request.UserRequestCreate;
import com.jbcamp1.moonlighthotel.dto.user.request.UserRequestUpdate;
import com.jbcamp1.moonlighthotel.dto.user.response.UserResponse;
import com.jbcamp1.moonlighthotel.dto.user.response.UserRoomReservationResponse;
import com.jbcamp1.moonlighthotel.enumeration.RolePrefix;
import com.jbcamp1.moonlighthotel.model.Role;
import com.jbcamp1.moonlighthotel.model.RoomReservation;
import com.jbcamp1.moonlighthotel.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper {

    Set<Role> rolesStringToRole(Set<String> roles);

    @Mapping(target = "name", source = "roles")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    Role roles(String roles);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "roomReservations", ignore = true)
    User toUser(UserRequestUpdate userRequestUpdate);

    default String roleToString(Role role) {
        return role.getName().substring(RolePrefix.ROLE_.lengthRolePrefix()).toLowerCase();
    }

    Set<String> roleToRoleName(Set<Role> roles);

    UserResponse toUserResponse(User user);

    @Mapping(source = "userRequestCreate.roles", ignore = true, target = "roles")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "roomReservations", ignore = true)
    User toUserFromClientCreate(UserRequestCreate userRequestCreate);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "roomReservations", ignore = true)
    User toUserFromAdminCreate(UserRequestCreate userRequestCreate);

    @Mapping(target = "user", expression = "java(roomReservation.getUser().getFullName())")
    @Mapping(target = "room", expression = "java(roomReservation.getRoom().getTitle())")
    @Mapping(source = "created", target = "date")
    UserRoomReservationResponse toUserRoomReservationsResponse(RoomReservation roomReservation);
}
