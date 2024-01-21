package com.example.demo.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppUtils {

	@Autowired
	private ModelMapper modelMapper;

	public  <S, D> D copyToObject(S source, Class<D> detsinationType) {
		return modelMapper.map(source, detsinationType);
	}

	public <S, D> List<D> copyList(List<S> sourceList, Class<D> detsinationType) {
		return sourceList.stream().map(source -> modelMapper.map(source, detsinationType)).collect(Collectors.toList());

	}

}
