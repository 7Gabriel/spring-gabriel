package br.com.cursomc.controllers.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {

	public static List<Integer> decodeIntList(String param){
		return Arrays.asList(param.split(",")).stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
	}
	
	public static String decodeParam(String param){
		try {
			return URLDecoder.decode(param, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
}
