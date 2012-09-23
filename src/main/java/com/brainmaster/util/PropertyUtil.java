package com.brainmaster.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class PropertyUtil extends PropertyPlaceholderConfigurer {
	private static Map<String, String> propertiesMap;

	@SuppressWarnings({ "rawtypes", "deprecation" })
	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)
			throws BeansException {
		super.processProperties(beanFactory, props);
		propertiesMap = new HashMap<String, String>();
		for (Object key : props.keySet()) {
			String keyStr = (String) key;
			propertiesMap.put(keyStr, parseStringValue(props.getProperty(keyStr), props, new HashSet()));
		}
	}

	public String getProperty(String name) {
		return propertiesMap.get(name);
	}

	@Override
	public void setProperties(Properties properties) {
		for (Object key : properties.keySet()) {
			if (propertiesMap.containsKey(key)) {
				propertiesMap.put((String) key, properties.getProperty((String) key));
			}
		}
		Properties defaultProps = new Properties();
		defaultProps.putAll(propertiesMap);
		super.setProperties(defaultProps);
	}
}