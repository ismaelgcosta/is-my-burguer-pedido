package br.com.ismyburguer.pedido.usecase.impl;

import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;

@IncludeEngines("cucumber")
@SelectClasspathResource("features")
public class RunAllCucumberTests {
}