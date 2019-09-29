package com.recognition.demo.liebiao;

import android.support.annotation.NonNull;

class BinderNotFoundException extends RuntimeException {

    BinderNotFoundException(@NonNull Class<?> clazz) {
        super("Do you have registered the binder for {className}.class in the adapter/pool?"
                .replace("{className}", clazz.getSimpleName()));
    }
}
