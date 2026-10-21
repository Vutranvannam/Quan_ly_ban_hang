package com.example.qly_kho.dto.request.user;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserCreateRequest(

    @NotBlank(message = "Username không được để trống")
    @Size(min = 3, max = 50, message = "Username phải từ 3 đến 50 ký tự")
    String username,

    @Size(max = 100, message = "Họ và tên tối đa 100 ký tự")
    String fullName,

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    String email,

    @JsonProperty("selectedRoles")
    @NotNull(message = "Danh sách vai trò không được null")
    @Size(min = 1, message = "Phải chọn ít nhất 1 vai trò")
    List<Long> roleIds,

    @JsonProperty("selectedStatus")
    @NotNull(message = "Trạng thái không được null")
    Boolean status

) {}