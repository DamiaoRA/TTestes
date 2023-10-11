package edu.ifpb.util;

import org.springframework.stereotype.Component;

@Component
public class AppUtils {
	public String toUpperCase(String s) {
		if(s == null)
			return "";
		return s.toUpperCase();
	}
}