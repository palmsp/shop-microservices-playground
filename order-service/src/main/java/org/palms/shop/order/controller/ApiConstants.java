package org.palms.shop.order.controller;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApiConstants {

    private static final String API_VERSION = "/v1";
    public static final String API_PREFIX = "/api" + API_VERSION;
    public static final String ID = "/{id}";
    public static final String ALL = "/all";

    public static final String ORDER = API_PREFIX + "/order";
}
