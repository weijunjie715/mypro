package org.mybatis.generator.plugins.comment;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class MyCommentGenerator implements CommentGenerator {

	private Properties properties;

	private boolean suppressDate;

	private boolean suppressAllComments;

	/** If suppressAllComments is true, this option is ignored. */
	private boolean addRemarkComments;

	private SimpleDateFormat dateFormat;

	public void addConfigurationProperties(Properties properties) {
		suppressAllComments = Boolean.parseBoolean(properties.getProperty("suppressAllComments"));
	}

	/**
	 * @Description 对象字段注释
	 **/
	public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
		if(!suppressAllComments){
			return;
		}

		if (introspectedColumn.getRemarks() != null && !introspectedColumn.getRemarks().equals("")) {
			field.addJavaDocLine("/**");
			field.addJavaDocLine(" * " + introspectedColumn.getRemarks());
			// addJavadocTag(field, false);
			field.addJavaDocLine(" */");
		}
	}

	/**
	 * @Description 对象字段注释
	 **/
	public void addFieldComment(Field field, IntrospectedTable introspectedTable) {

	}

	public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

	}
	/**
	 * @Description 添加类对象注释
	 **/
	public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {

	}

	public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean b) {

	}

	public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {

	}

	public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {

	}

	public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {

	}

	/**
	 * @Description 添加mapper接口方法注释
	 **/
	public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
		if (suppressAllComments) {
			return;
		}

		StringBuilder sb = new StringBuilder();
		method.addJavaDocLine("/**");
		sb.append(" * ");
		if (method.isConstructor()) {
			sb.append(" 构造查询条件");
		}
		String method_name = method.getName();
		if ("setOrderByClause".equals(method_name)) {
			sb.append(" 设置排序字段");
		} else if ("setDistinct".equals(method_name)) {
			sb.append(" 设置过滤重复数据");
		} else if ("getOredCriteria".equals(method_name)) {
			sb.append(" 获取当前的查询条件实例");
		} else if ("isDistinct".equals(method_name)) {
			sb.append(" 是否过滤重复数据");
		} else if ("getOrderByClause".equals(method_name)) {
			sb.append(" 获取排序字段");
		} else if ("createCriteria".equals(method_name)) {
			sb.append(" 创建一个查询条件");
		} else if ("createCriteriaInternal".equals(method_name)) {
			sb.append(" 内部构建查询条件对象");
		} else if ("clear".equals(method_name)) {
			sb.append(" 清除查询条件");
		} else if ("countByExample".equals(method_name)) {
			sb.append(" 根据指定的条件获取数据库记录数");
		} else if ("deleteByExample".equals(method_name)) {
			sb.append(" 根据指定的条件删除数据库符合条件的记录");
		} else if ("deleteByPrimaryKey".equals(method_name)) {
			sb.append(" 根据主键删除数据库的记录");
		} else if ("insert".equals(method_name)) {
			sb.append(" 新写入数据库记录");
		} else if ("insertSelective".equals(method_name)) {
			sb.append(" 动态字段,写入数据库记录");
		} else if ("selectByExample".equals(method_name)) {
			sb.append(" 根据指定的条件查询符合条件的数据库记录");
		} else if ("selectByPrimaryKey".equals(method_name)) {
			sb.append(" 根据指定主键获取一条数据库记录");
		} else if ("updateByExampleSelective".equals(method_name)) {
			sb.append(" 动态根据指定的条件来更新符合条件的数据库记录");
		} else if ("updateByExample".equals(method_name)) {
			sb.append(" 根据指定的条件来更新符合条件的数据库记录");
		} else if ("updateByPrimaryKeySelective".equals(method_name)) {
			sb.append(" 动态字段,根据主键来更新符合条件的数据库记录");
		} else if ("updateByPrimaryKey".equals(method_name)) {
			sb.append(" 根据主键来更新符合条件的数据库记录");
		}
		sb.append(":");
		sb.append(introspectedTable.getFullyQualifiedTable());
		method.addJavaDocLine(sb.toString());

		final List<Parameter> parameterList = method.getParameters();
		if (!parameterList.isEmpty()) {
			method.addJavaDocLine(" *");
			if ("or".equals(method_name)) {
				sb.append(" 增加或者的查询条件,用于构建或者查询");
			}
		} else {
			if ("or".equals(method_name)) {
				sb.append(" 创建一个新的或者查询条件");
			}
		}
		String paramterName;
		for (Parameter parameter : parameterList) {
			sb.setLength(0);
			sb.append(" * @param "); //$NON-NLS-1$
			paramterName = parameter.getName();
			sb.append(paramterName);
			if ("orderByClause".equals(paramterName)) {
				sb.append(" 排序字段"); //$NON-NLS-1$
			} else if ("distinct".equals(paramterName)) {
				sb.append(" 是否过滤重复数据");
			} else if ("criteria".equals(paramterName)) {
				sb.append(" 过滤条件实例");
			}
			method.addJavaDocLine(sb.toString());
		}

		method.addJavaDocLine(" */");
	}

	public void addJavaFileComment(CompilationUnit compilationUnit) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		compilationUnit.addFileCommentLine("/*");
		compilationUnit.addFileCommentLine("* "+compilationUnit.getType().getShortName()+".java");
		compilationUnit.addFileCommentLine("* Copyright(C) 2020-2025 myPro");
		compilationUnit.addFileCommentLine("* @date "+sdf.format(new Date())+"");
		compilationUnit.addFileCommentLine("*/");
	}

	public void addComment(XmlElement xmlElement) {

	}

	public void addRootComment(XmlElement xmlElement) {

	}

	@Override
	public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> imports) {

	}

	@Override
	public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> imports) {

	}

	@Override
	public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> imports) {

	}

	@Override
	public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> imports) {

	}

	@Override
	public void addClassAnnotation(InnerClass innerClass, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> imports) {

	}
}
