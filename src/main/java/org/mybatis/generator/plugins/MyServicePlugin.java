package org.mybatis.generator.plugins;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;


/**
 * @author https://blog.csdn.net/qq_28898309/article/details/106812466
 */
public class MyServicePlugin extends PluginAdapter{

    // 项目目录，一般为 src/main/java
    private String targetProject;

    // service包名，如：com.thinkj2ee.cms.service.service
    private String servicePackage;

    // service实现类包名，如：com.thinkj2ee.cms.service.service.impl
    private String serviceImplPackage;
    // service接口名前缀
    private String servicePreffix;

    // service接口名后缀
    private String serviceSuffix;

    // service接口的父接口
    private String superServiceInterface;

    // service实现类的父类
    private String superServiceImpl;

    // dao接口基类
    private String superDaoInterface;

    // Example类的包名
    private String examplePacket;

    private String recordType;

    private String modelName;

    private FullyQualifiedJavaType model;

    private String serviceName;
    private String serviceImplName;

    @Override
    public boolean validate(List<String> warnings) {
        boolean valid = true;

       /* if (!stringHasValue(properties
                .getProperty("targetProject"))) { //$NON-NLS-1$
            warnings.add(getString("ValidationError.18", //$NON-NLS-1$
                    "MapperConfigPlugin", //$NON-NLS-1$
                    "targetProject")); //$NON-NLS-1$
            valid = false;
        }
        if (!stringHasValue(properties.getProperty("servicePackage"))) { //$NON-NLS-1$
            warnings.add(getString("ValidationError.18", //$NON-NLS-1$
                    "MapperConfigPlugin", //$NON-NLS-1$
                    "servicePackage")); //$NON-NLS-1$
            valid = false;
        }
        if (!stringHasValue(properties.getProperty("serviceImplPackage"))) { //$NON-NLS-1$
            warnings.add(getString("ValidationError.18", //$NON-NLS-1$
                    "MapperConfigPlugin", //$NON-NLS-1$
                    "serviceImplPackage")); //$NON-NLS-1$
            valid = false;
        }
*/
        targetProject = properties.getProperty("targetProject");
        servicePackage = properties.getProperty("servicePackage");
        serviceImplPackage = properties.getProperty("serviceImplPackage");
        servicePreffix = properties.getProperty("servicePreffix");
        servicePreffix = stringHasValue(servicePreffix) ? servicePreffix : "";
        serviceSuffix = properties.getProperty("serviceSuffix");
        serviceSuffix = stringHasValue(serviceSuffix) ? serviceSuffix : "";
        superServiceInterface = properties.getProperty("superServiceInterface");
        superServiceImpl = properties.getProperty("superServiceImpl");
        superDaoInterface = properties.getProperty("superDaoInterface");
        return valid;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        recordType = introspectedTable.getBaseRecordType();
        modelName = recordType.substring(recordType.lastIndexOf(".") + 1);
        model = new FullyQualifiedJavaType(recordType);
        serviceName = servicePackage + "." + servicePreffix + modelName + serviceSuffix;
        serviceImplName = serviceImplPackage + "." + modelName + serviceSuffix+"Impl";
        examplePacket=recordType.substring(0,recordType.lastIndexOf("."));
        List<GeneratedJavaFile> answer = new ArrayList<>();
        GeneratedJavaFile gjf = generateServiceInterface(introspectedTable);
        GeneratedJavaFile gjf2 = generateServiceImpl(introspectedTable);
        answer.add(gjf);
        answer.add(gjf2);
        return answer;
    }

    /**
     * @Description 生成service 接口
     * @Date 2020/6/28 15:42
     **/
    private GeneratedJavaFile generateServiceInterface(IntrospectedTable introspectedTable) {
        //获取当前主键字段类型
        FullyQualifiedJavaType pkcFieldJavaType = getPKCFieldType(introspectedTable);
        //获取当前主键类型名字（泛型使用）
        String pkcFieldTypeName = getPKCFieldTypeName(introspectedTable);

        FullyQualifiedJavaType service = new FullyQualifiedJavaType(serviceName);
        Interface serviceInterface = new Interface(service);
        serviceInterface.setVisibility(JavaVisibility.PUBLIC);
        //添加生成service接口的doc
        serviceInterface.addJavaDocLine("/**");
        serviceInterface.addJavaDocLine(" * MyBatis Generator工具自动生成");
        serviceInterface.addJavaDocLine(" */");
        // 添加父接口
        if(stringHasValue(superServiceInterface)) {
            String superServiceInterfaceName = superServiceInterface.substring(superServiceInterface.lastIndexOf(".") + 1);
            serviceInterface.addImportedType(new FullyQualifiedJavaType(superServiceInterface));
            serviceInterface.addImportedType(new FullyQualifiedJavaType(recordType));
            serviceInterface.addImportedType(pkcFieldJavaType);
            serviceInterface.addSuperInterface(new FullyQualifiedJavaType(superServiceInterfaceName + "<" + modelName + ","+pkcFieldTypeName+">"));
        }
        GeneratedJavaFile gjf = new GeneratedJavaFile(serviceInterface, targetProject, context.getJavaFormatter());
        return gjf;
    }

    /**
     * @Description 生成serviceImpl
     * @Date 2020/6/28 15:41
     **/
    private GeneratedJavaFile generateServiceImpl(IntrospectedTable introspectedTable) {
        //获取当前主键字段类型
        FullyQualifiedJavaType pkcFieldJavaType = getPKCFieldType(introspectedTable);
        //获取当前主键类型名字（泛型使用）
        String pkcFieldTypeName = getPKCFieldTypeName(introspectedTable);

        FullyQualifiedJavaType service = new FullyQualifiedJavaType(serviceName);
        FullyQualifiedJavaType serviceImpl = new FullyQualifiedJavaType(serviceImplName);
        TopLevelClass clazz = new TopLevelClass(serviceImpl);
        //添加生成service的doc
        clazz.addJavaDocLine("/**");
        clazz.addJavaDocLine(" * MyBatis Generator工具自动生成");
        clazz.addJavaDocLine(" */");
        //描述类的作用域修饰符
        clazz.setVisibility(JavaVisibility.PUBLIC);
        //描述类 引入的类
        clazz.addImportedType(service);
        //描述类 的实现接口类
        clazz.addSuperInterface(service);
        if(stringHasValue(superServiceImpl)) {
            String superServiceImplName = superServiceImpl.substring(superServiceImpl.lastIndexOf(".") + 1);
            clazz.addImportedType(superServiceImpl);
            clazz.addImportedType(recordType);
            clazz.addImportedType(pkcFieldJavaType);
            clazz.setSuperClass(superServiceImplName + "<" + modelName + ","+pkcFieldTypeName+">");
        }
        clazz.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Service"));
        clazz.addAnnotation("@Service");

        String daoFieldType = introspectedTable.getMyBatis3JavaMapperType();
        String daoFieldName = firstCharToLowCase(daoFieldType.substring(daoFieldType.lastIndexOf(".") + 1));
        //描述类的成员属性
        Field daoField = new Field(daoFieldName, new FullyQualifiedJavaType(daoFieldType));
        clazz.addImportedType(new FullyQualifiedJavaType(daoFieldType));
        clazz.addImportedType(new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired"));
        //描述成员属性 的注解
        daoField.addAnnotation("@Autowired");
        //描述成员属性修饰符
        daoField.setVisibility(JavaVisibility.PRIVATE);
        clazz.addField(daoField);

        //创建方法名
        String implName = serviceImplName.substring(serviceImplName.lastIndexOf(".") + 1);
        Method method = new Method(implName);
        //添加方法参数
        Parameter parameter = new Parameter(new FullyQualifiedJavaType(daoFieldType),"mapper");
        method.addParameter(parameter);
        //方法体，逻辑代码
        method.addBodyLine("this." + daoFieldName + "= mapper;");
        method.addBodyLine("init(" + daoFieldName + ");");
        //修饰符
        method.setVisibility(JavaVisibility.PUBLIC);
        //设置方法为构造方法
        method.setConstructor(true);
        clazz.addMethod(method);

        /*Method method1 = new Method("getExample");
        method1.addAnnotation("@Override");
        FullyQualifiedJavaType methodReturnType1 = new FullyQualifiedJavaType("Object");
        clazz.addImportedType(new FullyQualifiedJavaType(examplePacket.concat(".").concat(modelName).concat("Example")));
        method1.setReturnType(methodReturnType1);
        method1.addBodyLine("return new " + modelName + "Example();");
        method1.setVisibility(JavaVisibility.PUBLIC);
        clazz.addMethod(method1);*/

        GeneratedJavaFile gjf2 = new GeneratedJavaFile(clazz, targetProject, context.getJavaFormatter());
        return gjf2;
    }




    private String firstCharToLowCase(String str) {
        char[] chars = new char[1];
        //String str="ABCDE1234";
        chars[0] = str.charAt(0);
        String temp = new String(chars);
        if(chars[0] >= 'A'  &&  chars[0] <= 'Z') {
            return str.replaceFirst(temp,temp.toLowerCase());
        }
        return str;
    }

    /**
     * @Description 获取当前表对象主键类型
     * @Date 2020/6/28 15:33
     **/
    private FullyQualifiedJavaType getPKCFieldType(IntrospectedTable introspectedTable){
        List<IntrospectedColumn> primaryKeyColumns = introspectedTable.getPrimaryKeyColumns();
        //设置默认主键PK泛型类型（数据库读取不到主键情况下使用）
        FullyQualifiedJavaType f2 = new FullyQualifiedJavaType("java.lang.String");
        //获取主键泛型成功  只取第一个  没有主键则不取
        for(IntrospectedColumn pkc:primaryKeyColumns){
            f2 = pkc.getFullyQualifiedJavaType();
            break;
        }
        return f2;
    }

    /**
     * @Description 获取当前表对象主键类型
     * @Date 2020/6/28 15:33
     **/
    private String getPKCFieldTypeName(IntrospectedTable introspectedTable){
        List<IntrospectedColumn> primaryKeyColumns = introspectedTable.getPrimaryKeyColumns();
        //设置默认主键PK泛型类型（数据库读取不到主键情况下使用）
        String s = "String";
        //获取主键泛型成功  只取第一个  没有主键则不取
        for(IntrospectedColumn pkc:primaryKeyColumns){
            s = pkc.getFullyQualifiedJavaType().getShortName();
            break;
        }
        return s;
    }

}