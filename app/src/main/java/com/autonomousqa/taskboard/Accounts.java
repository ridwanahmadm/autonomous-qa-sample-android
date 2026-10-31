package com.autonomousqa.taskboard;

/** The accounts the app knows. A real app would ask its server. */
final class Accounts {
    private static final String DEMO_EMAIL = "demo@example.com";
    private static final String DEMO_PASSWORD = "demo-pass-123";

    private Accounts() {}

    static boolean check(String email, String password) {
        return DEMO_EMAIL.equalsIgnoreCase(email) && DEMO_PASSWORD.equals(password);
    }
}
