package com.sakila.util;
import java.util.regex.Pattern;
public final class Validators {
    private Validators(){}
    // Fecha ISO básica: YYYY-MM-DD
    public static final Pattern DATE_ISO = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
    // Cédula RD (formato típico): 000-0000000-0
    public static final Pattern CEDULA_RD = Pattern.compile("^\\d{3}-\\d{7}-\\d$");
    // SSN US: 000-00-0000
    public static final Pattern SSN_US = Pattern.compile("^\\d{3}-\\d{2}-\\d{4}$");
    // Teléfono internacional simple: +N... con 8-15 dígitos
    public static final Pattern PHONE_INTL = Pattern.compile("^\\+\\d{8,15}$");
    public static boolean isDate(String s){ return s != null && DATE_ISO.matcher(s).matches(); }
    public static boolean isCedulaRD(String s){ return s != null && CEDULA_RD.matcher(s).matches(); }
    public static boolean isSSN(String s){ return s != null && SSN_US.matcher(s).matches(); }
    public static boolean isPhoneIntl(String s){ return s != null && PHONE_INTL.matcher(s).matches(); }
}