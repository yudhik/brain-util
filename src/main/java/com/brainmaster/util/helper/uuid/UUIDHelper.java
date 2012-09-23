package com.brainmaster.util.helper.uuid;

import java.util.UUID;

public class UUIDHelper {

	private static final char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
			'E', 'F' };

	private static int hexValue(char c) {
		if (c >= '0' && c <= '9') return (c - '0');
		if (c >= 'A' && c <= 'F') return 10 + (c - 'A');
		throw new IllegalArgumentException("Invalid hexadecimal character " + c);
	}

	public static String uuidToString(UUID uuid) {
		if (uuid == null) return null;
		char[] result = new char[32];
		long hi = uuid.getMostSignificantBits();
		long lo = uuid.getLeastSignificantBits();
		int i = 15;
		int j = 31;
		while (i >= 0) {
			result[i--] = hexDigits[(int) hi & 0xF];
			hi >>= 4;
			result[j--] = hexDigits[(int) lo & 0xF];
			lo >>= 4;
		}
		return String.valueOf(result);
	}

	public static UUID stringToUUID(String value) {
		if ((value == null) || "".equals(value)) return null;
		if (value.length() != 32) throw new IllegalArgumentException("Value should have 32 characters (" + value + ")");
		long hi = 0L;
		long lo = 0L;
		int i = 0;
		int j = 16;
		while (i < 16) {
			hi = (hi << 4) | hexValue(value.charAt(i++));
			lo = (lo << 4) | hexValue(value.charAt(j++));
		}
		return new UUID(hi, lo);
	}

	public static UUID objectToUUID(Object value) {
		return ((value == null) || (value instanceof UUID)) ? (UUID) value : stringToUUID(value.toString());
	}
}