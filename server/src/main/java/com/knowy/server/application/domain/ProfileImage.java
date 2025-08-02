package com.knowy.server.application.domain;

import java.io.Serializable;

public record ProfileImage(Integer id, String url) implements Serializable {
}
