package com.knowy.server.domain;

import java.io.Serializable;

public record ProfileImage(Integer id, String url) implements Serializable {
}
