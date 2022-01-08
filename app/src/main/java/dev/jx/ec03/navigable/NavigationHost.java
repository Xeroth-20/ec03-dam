package dev.jx.ec03.navigable;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public interface NavigationHost {

    void navigateTo(@NonNull Fragment fragment, boolean addToBackStack);
}
