package com.example.lib;


import com.example.annotation.Inter;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

import io.reactivex.annotations.NonNull;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes({"com.example.annotation.Inter"})
public class InterProcessor extends AbstractProcessor {
    private Filer filerUtils;
    private Elements elementUtils;
    private String mBaseService;
    private String mHandlerResponseFunc;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filerUtils = processingEnv.getFiler();
        elementUtils = processingEnv.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        System.out.print("process========================================================\n");
        Set<? extends Element> elements = roundEnvironment
                .getElementsAnnotatedWith(Inter.class);

        ArrayList<MethodSpec> methodSpecs = new ArrayList<>();
        for (Element element : elements) {
            if (element instanceof TypeElement) {
                Inter inter = element.getAnnotation(Inter.class);
                String type = inter.type();
                if ("BaseService".equals(type)) {
                    mBaseService = inter.value();
                }
                if ("HandleResponseFunc".equalsIgnoreCase(type)) {
                    mHandlerResponseFunc = inter.value();
                }
            } else if (element instanceof ExecutableElement) {
                elementUtils.getPackageOf(element);
                MethodObj methodObj = catagoryMethod(element);
                MethodSpec methodSpec = generateMethod(methodObj);
                methodSpecs.add(methodSpec);
            }
        }

        generateClz(methodSpecs);
        return false;
    }

    private void generateClz(Iterable<MethodSpec> methods) {
        TypeSpec helloWorld = TypeSpec.classBuilder("ApiService")
                .addModifiers(Modifier.PUBLIC)
                .superclass(ClassName.get(mBaseService.substring(0, mBaseService.lastIndexOf(".")),
                        mBaseService.substring(mBaseService.lastIndexOf(".") + 1)))
                .addMethods(methods)
                .build();
        try {
            JavaFile.builder(Constants.PACKAGE_OF_GENERATE_FILE, helloWorld).build().writeTo(filerUtils);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private MethodSpec generateMethod(MethodObj methodObj) {
        List<ClassObj> returnParamsType = methodObj.getReturnType();
        List<ClassObj> newReturnParamsType = copyList(returnParamsType);
        removeResponseObj(newReturnParamsType);

        ParameterizedTypeName newreturnParameteredTypeName = null;
        if (newReturnParamsType.size() > 1) {
            newreturnParameteredTypeName = getReturnParameteredTypeName(newReturnParamsType);
        }

        List<ClassObj> methodParamsType = methodObj.getMethodParamsType();
        List<ClassObj> newMethodParamsType = copyList(methodParamsType);
        // remote Request class.
        newMethodParamsType.remove(0);

        ParameterizedTypeName newMethodParamsTypeName = null;
        if (newMethodParamsType.size() > 1) {
            newMethodParamsTypeName = getReturnParameteredTypeName(
                    newMethodParamsType);
        }

        ParameterizedTypeName methodParamsTypeName = null;
        if (methodParamsType.size() > 1) {
            methodParamsTypeName = getReturnParameteredTypeName(
                    methodParamsType);
        }

        return MethodSpec.methodBuilder(methodObj.getMethodName())
                .addModifiers(Modifier.PUBLIC)
                .addParameter(newMethodParamsTypeName == null ? ClassName.get(
                        newMethodParamsType.get(0).getPackageName(),
                        newMethodParamsType.get(0).getClassName()) : newMethodParamsTypeName
                        , "req")
                .returns(newreturnParameteredTypeName == null ? ClassName.get(
                        newMethodParamsType.get(0).getPackageName(),
                        newMethodParamsType.get(0).getClassName()) : newreturnParameteredTypeName)
                .addStatement("$N request = genRequest($N)",
                        methodParamsTypeName == null ? methodParamsType.get(0).getPackageName() + methodParamsType.get(
                                0).getClassName() : methodParamsTypeName.toString(), "req")
                .addStatement(
                        "return getApiService().$N($N).map(new $N<>()).compose(apply"
                                + "($N))",
                        methodObj.getMethodName(), "request", mHandlerResponseFunc, "request")
                .build();
    }

    private void removeResponseObj(List<ClassObj> newReturnParamsType) {
        Iterator<ClassObj> iterator = newReturnParamsType.iterator();
        while (iterator.hasNext()) {
            ClassObj classObj = iterator.next();
            if ("Response".equalsIgnoreCase(classObj.getClassName())) {
                iterator.remove();
            }
        }
    }

    private List<ClassObj> copyList(List<ClassObj> methodParamsType) {
        return new ArrayList<>(methodParamsType);
    }

    private ParameterizedTypeName getReturnParameteredTypeName(List<ClassObj> returnType) {
        int size = returnType.size();
        if (2 == size) {
            return ParameterizedTypeName.get(
                    ClassName.get(returnType.get(0).getPackageName(), returnType.get(0).getClassName()),
                    ClassName.get(returnType.get(1).getPackageName(), returnType.get(1).getClassName()));
        } else if (3 == size) {
            return ParameterizedTypeName.get(
                    ClassName.get(returnType.get(0).getPackageName(), returnType.get(0).getClassName()),
                    ParameterizedTypeName.get(
                            ClassName.get(returnType.get(1).getPackageName(), returnType.get(1)
                                    .getClassName()),
                            ClassName.get(returnType.get(2).getPackageName(), returnType.get(2).getClassName())));
        } else {
            throw new RuntimeException("only support generic type <= 3 or >=2 layer.");
        }
    }

    private MethodObj catagoryMethod(Element element) {
        if (element == null) {
            return null;
        }

        List<ClassObj> catagoryReturnTypeObj = new ArrayList<>();
        List<ClassObj> catagoryMethodParamsObj = new ArrayList<>();

        MethodObj methodObj = new MethodObj();
        if (element instanceof ExecutableElement) {
            ExecutableElement methodElement = (ExecutableElement) element;
            methodObj.setMethodName(methodElement.getSimpleName().toString());
            methodObj.setReturnType(catagoryPackage(catagoryReturnTypeObj, methodElement.getReturnType().toString()));
            if (methodElement.getParameters().size() > 0) {
                methodObj.setMethodParamsType(
                        catagoryPackage(catagoryMethodParamsObj, methodElement.getParameters().get(
                                0).asType().toString()));
            }
        }
        return methodObj;
    }

    private List<ClassObj> catagoryPackage(List<ClassObj> catagoryObj, String returnTypeStr) {
        if (null == returnTypeStr || returnTypeStr.length() == 0) {
            return catagoryObj;
        }

        int firstPosition = returnTypeStr.indexOf("<");
        int secondPosition = returnTypeStr.lastIndexOf(">");
        String outerClassType;
        String innerClassType = "";
        if (firstPosition == -1 || secondPosition == -1) {
            outerClassType = returnTypeStr;
        } else {
            outerClassType = returnTypeStr.substring(0, firstPosition);
            innerClassType = returnTypeStr.substring(firstPosition + 1, secondPosition);
        }
        int classSplitPosition = outerClassType.lastIndexOf(".");
        String classPackageName = outerClassType.substring(0, classSplitPosition);
        String className = outerClassType.substring(classSplitPosition + 1);
        ClassObj classObj = new ClassObj(classPackageName, className);
        catagoryObj.add(classObj);
        return catagoryPackage(catagoryObj, innerClassType);
    }
}
