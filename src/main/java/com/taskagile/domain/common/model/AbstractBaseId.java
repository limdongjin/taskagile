package com.taskagile.domain.common.model;

import java.io.Serializable;

public abstract class AbstractBaseId implements Serializable {
    private static final long serialVersionUID = 3030037620812737224L;

    private long id;

    public AbstractBaseId(long id) {
        this.id = id;
    }

    public long value() {
        return id;
    }

    public boolean isValid() {
        return id > 0;
    }
}