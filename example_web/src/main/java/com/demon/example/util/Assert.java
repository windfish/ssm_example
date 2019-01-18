package com.demon.example.util;

public abstract class Assert {
	
	public static void isValidKey(Long key) {
		Assert.notNull(key, "[Assertion failed] - The key is required; It must not be null");
		if (key <= 0) {
			throw new IllegalArgumentException("[Assertion failed] - The key is invalid; The key should > 0.");
		}
	}

	public static void isValidKey(Integer key) {
		Assert.notNull(key, "[Assertion failed] - The key is required; It must not be null");
		if (key <= 0) {
			throw new IllegalArgumentException("[Assertion failed] - The key is invalid; The key should > 0.");
		}
	}

	public static void isValidKey(String key) {
		Assert.notNull(key, "[Assertion failed] - The key is required; It must not be null");
		if ("".equals(key.trim())) {
			throw new IllegalArgumentException("[Assertion failed] - The key is invalid; The key should not be empty or blank.");
		}
	}

	public static void isValidPageIndex(int pageIndex) {
		Assert.isTrue(pageIndex >= 0, "[Assertion failed] - The argument pageIndex is invalid (pageIndex should >= 0).");
	}

	public static void isValidMaxCount(int maxCount) {
		Assert.isTrue(maxCount > 0, "[Assertion failed] - The argument maxCount is invalid (maxCount should > 0).");
	}

	public static void isTrue(boolean expression, String message) {
		if (!expression) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void isNull(Object object, String message) {
		if (object != null) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void isNull(Object object) {
		isNull(object, "[Assertion failed] - the object argument must be null");
	}

	public static void notNull(Object object, String message) {
		if (object == null) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void notNull(Object object) {
		notNull(object, "[Assertion failed] - this argument is required; it must not be null");
	}
}
