/*
 * Copyright 2019 slavb.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.ilb.filedossier;

import java.net.URISyntaxException;
import org.apache.cxf.ext.logging.LoggingFeature;
import org.apache.cxf.jaxrs.provider.json.JsonMapObjectProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.relational.core.mapping.NamingStrategy;
import ru.ilb.common.jaxrs.jaxb.JaxbContextResolver;
import ru.ilb.filedossier.context.persistence.DossierContextNamingStrategy;
import ru.ilb.filedossier.ddl.DossierDefinitionRepository;
import ru.ilb.filedossier.ddl.FileDossierDefinitionRepository;
import ru.ilb.filedossier.store.StoreFactory;

/**
 *
 * @author slavb
 */
@SpringBootApplication
@EnableJdbcRepositories(basePackages = "ru.ilb.filedossier.context.persistence.repositories")
@ComponentScan //(basePackages = {"ru.ilb.filedossier.components", "ru.ilb.filedossier.mappers"})
@Configuration
public class Application { // extends JpaBaseConfiguration

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ru.ilb.common.jaxrs.json.MOXyJsonProvider jsonProvider() {
        // lacks @Provider annotation
        // return new org.eclipse.persistence.jaxb.rs.MOXyJsonProvider();
        return new ru.ilb.common.jaxrs.json.MOXyJsonProvider();
    }

    @Bean
    public JaxbContextResolver jaxbContextResolver() {
        return new JaxbContextResolver();
    }

    @Bean
    public StoreFactory storeFactory() {
        try {
            return StoreFactory.newInstance(getClass()
                    .getClassLoader()
                    .getResource("teststoreroot")
                    .toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException("Incorrect store root path: " + e);
        }
    }

    @Bean
    public DossierDefinitionRepository dossierDefinitionRepository() {
        try {
            return new FileDossierDefinitionRepository(getClass()
                    .getClassLoader()
                    .getResource("models")
                    .toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException("Incorrect definition path: " + e);
        }
    }

//    @Bean
//    @ConditionalOnExpression("'${ILB_SYSID}'=='DEVEL'")
//    public XSLTRequestFilter xsltRequestFilter() {
//        // REFRESH TEMPLATES
//        return new XSLTRequestFilter();
//    }
//
//    @Bean
//    public XSLTJaxbProvider xsltJaxbProvider() {
//        XSLTJaxbProvider xsltJaxbProvider = new XSLTJaxbProvider();
//        xsltJaxbProvider.setResolver(new ServletContextURIResolver());
//        xsltJaxbProvider.setRefreshTemplates(true);
//        xsltJaxbProvider.setProduceMediaTypes(Arrays.asList("application/xhtml+xml,text/csv,application/pdf"));
//        xsltJaxbProvider.setOutTemplate("classpath:/stylesheets/filedossier/dossier.xsl");
//        xsltJaxbProvider.setRefreshTemplates(true);
//        return xsltJaxbProvider;
//    }
    @Bean
    public LoggingFeature loggingFeature() {
        LoggingFeature lf = new LoggingFeature();
        lf.addBinaryContentMediaTypes("application/vnd.oasis.opendocument.spreadsheet");
        return lf;
    }

    @Bean
    public NamingStrategy namingStrategy() {
        return new DossierContextNamingStrategy();
    }

    @Bean
    public JsonMapObjectProvider jsonMapObjectProvider() {
        return new JsonMapObjectProvider();
    }
}
