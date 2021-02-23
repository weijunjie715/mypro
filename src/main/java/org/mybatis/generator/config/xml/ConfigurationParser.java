/**
 *    Copyright 2006-2018 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mybatis.generator.config.xml;

import org.mybatis.generator.codegen.XmlConstants;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.exception.XMLParserException;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

public class ConfigurationParser {

    private List<String> warnings;
    private List<String> parseErrors;
    private Properties extraProperties;

    public ConfigurationParser(List<String> warnings) {
        this(null, warnings);
    }

    /**
     * This constructor accepts a properties object which may be used to specify
     * an additional property set.  Typically this property set will be Ant or Maven properties
     * specified in the build.xml file or the POM.
     * 
     * <p>If there are name collisions between the different property sets, they will be 
     * resolved in this order:
     * 
     * <ol>
     *   <li>System properties take highest precedence</li>
     *   <li>Properties specified in the &lt;properties&gt; configuration
     *       element are next</li>
     *   <li>Properties specified in this "extra" property set are
     *       lowest precedence.</li>
     * </ol>
     * 
     * @param extraProperties an (optional) set of properties used to resolve property
     *     references in the configuration file
     * @param warnings any warnings are added to this array
     */
    public ConfigurationParser(Properties extraProperties, List<String> warnings) {
        super();
        this.extraProperties = extraProperties;

        if (warnings == null) {
            this.warnings = new ArrayList<>();
        } else {
            this.warnings = warnings;
        }

        parseErrors = new ArrayList<>();
    }

    /**
     * 解析组态
     * @param inputFile inputFile
     * @return
     * @throws IOException
     * @throws XMLParserException
     */
    public Configuration parseConfiguration(File inputFile) throws IOException,
            XMLParserException {
        //转换fileReader（字符读取流中数据）
        FileReader fr = new FileReader(inputFile);

        return parseConfiguration(fr);
    }

    public Configuration parseConfiguration(Reader reader) throws IOException,
            XMLParserException {

        InputSource is = new InputSource(reader);

        return parseConfiguration(is);
    }

    public Configuration parseConfiguration(InputStream inputStream)
            throws IOException, XMLParserException {

        InputSource is = new InputSource(inputStream);

        return parseConfiguration(is);
    }

    /**
     * 解析组态
     * @param inputSource inputSource
     * @return
     * @throws IOException
     * @throws XMLParserException
     */
    private Configuration parseConfiguration(InputSource inputSource)
            throws IOException, XMLParserException {
        parseErrors.clear();
        //创建xml解析工厂
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //设置是否解析文档
        factory.setValidating(true);

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setEntityResolver(new ParserEntityResolver());

            ParserErrorHandler handler = new ParserErrorHandler(warnings,
                    parseErrors);
            builder.setErrorHandler(handler);

            Document document = null;
            try {
                document = builder.parse(inputSource);
            } catch (SAXParseException e) {
                throw new XMLParserException(parseErrors);
            } catch (SAXException e) {
                if (e.getException() == null) {
                    parseErrors.add(e.getMessage());
                } else {
                    parseErrors.add(e.getException().getMessage());
                }
            }

            if (parseErrors.size() > 0) {
                throw new XMLParserException(parseErrors);
            }

            Configuration config;
            Element rootNode = document.getDocumentElement();
            DocumentType docType = document.getDoctype();
            if (rootNode.getNodeType() == Node.ELEMENT_NODE
                    && docType.getPublicId().equals(
                            XmlConstants.MYBATIS_GENERATOR_CONFIG_PUBLIC_ID)) {
                //进入解析xml配置文件
                config = parseMyBatisGeneratorConfiguration(rootNode);
            } else {
                throw new XMLParserException(getString("RuntimeError.5")); //$NON-NLS-1$
            }

            if (parseErrors.size() > 0) {
                throw new XMLParserException(parseErrors);
            }

            return config;
        } catch (ParserConfigurationException e) {
            parseErrors.add(e.getMessage());
            throw new XMLParserException(parseErrors);
        }
    }

    /**
     * 解析mybatis发生器组态
     * @param rootNode rootNode
     * @return
     * @throws XMLParserException
     */
    private Configuration parseMyBatisGeneratorConfiguration(Element rootNode)
            throws XMLParserException {
        MyBatisGeneratorConfigurationParser parser = new MyBatisGeneratorConfigurationParser(
                extraProperties);
        return parser.parseConfiguration(rootNode);
    }
}
