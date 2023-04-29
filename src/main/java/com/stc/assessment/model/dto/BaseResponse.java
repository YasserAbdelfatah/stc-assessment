package com.stc.assessment.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class BaseResponse<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private T data;
}
