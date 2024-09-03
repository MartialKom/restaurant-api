package com.groupe1.restaurant.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class OperationNonPermittedException extends RuntimeException {

    private final String errorMsg;

}