package org.alexdev.icarus.pooling;

public interface FutureHandler<R> {
    void onSuccess(R result);
    void onFailure(Throwable e);
}