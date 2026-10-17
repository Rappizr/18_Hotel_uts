package id.ac.polinema.uts.hotel;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Pembantu tes: mencari kelas, constructor, dan method lewat reflection.
 * Dengan cara ini file tes tetap bisa dikompilasi walaupun kelasmu belum dibuat,
 * dan kegagalan muncul sebagai pesan yang jelas, bukan error kompilasi.
 */
final class R {

    static final String PKG = "id.ac.polinema.uts.hotel.";

    private static final Map<Class<?>, Class<?>> BOXES = Map.of(
            int.class, Integer.class,
            double.class, Double.class,
            boolean.class, Boolean.class,
            long.class, Long.class);

    private R() {
    }

    static Class<?> cls(String simpleName) {
        try {
            return Class.forName(PKG + simpleName);
        } catch (ClassNotFoundException e) {
            return fail("Kelas " + simpleName + " belum ada. Buat kelas ini sesuai diagram di README.");
        }
    }

    static boolean exists(String simpleName) {
        try {
            Class.forName(PKG + simpleName);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    private static boolean fits(Class<?> param, Object arg) {
        if (arg == null) {
            return !param.isPrimitive();
        }
        Class<?> target = param.isPrimitive() ? BOXES.get(param) : param;
        return target != null && target.isInstance(arg);
    }

    private static boolean fitsAll(Class<?>[] params, Object[] args) {
        if (params.length != args.length) {
            return false;
        }
        for (int i = 0; i < params.length; i++) {
            if (!fits(params[i], args[i])) {
                return false;
            }
        }
        return true;
    }

    static Object create(String simpleName, Object... args) throws Throwable {
        Class<?> c = cls(simpleName);
        for (Constructor<?> k : c.getConstructors()) {
            if (fitsAll(k.getParameterTypes(), args)) {
                try {
                    return k.newInstance(args);
                } catch (InvocationTargetException e) {
                    throw e.getCause();
                }
            }
        }
        return fail("Constructor " + simpleName + " dengan " + args.length
                + " parameter (public) tidak ditemukan. Cocokkan dengan diagram.");
    }

    static Object call(Object target, String name, Object... args) throws Throwable {
        for (Method m : target.getClass().getMethods()) {
            if (m.getName().equals(name) && fitsAll(m.getParameterTypes(), args)) {
                try {
                    return m.invoke(target, args);
                } catch (InvocationTargetException e) {
                    throw e.getCause();
                }
            }
        }
        return fail("Method " + name + " dengan " + args.length + " parameter tidak ditemukan di kelas "
                + target.getClass().getSimpleName() + ". Cocokkan dengan diagram.");
    }

    static boolean hasPublicMethod(Class<?> c, String name) {
        for (Method m : c.getMethods()) {
            if (m.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    static boolean declaresMethod(Class<?> c, String name) {
        for (Method m : c.getDeclaredMethods()) {
            if (m.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    static void assertAllFieldsPrivate(Class<?> c) {
        for (Field f : c.getDeclaredFields()) {
            if (f.isSynthetic() || Modifier.isStatic(f.getModifiers())) {
                continue;
            }
            assertTrue(Modifier.isPrivate(f.getModifiers()),
                    "Field '" + f.getName() + "' di kelas " + c.getSimpleName()
                            + " harus private (enkapsulasi)");
        }
    }
}
