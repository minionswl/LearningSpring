package com.wl.spring.aop;

import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;
import org.aspectj.weaver.tools.ShadowMatch;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class AspectJExpressionPointcut implements Pointcut, ClassFilter, MethodMatcher {
    private PointcutParser pointcutParser;

    private String expression;

    private PointcutExpression pointcutExpression;

    private static final Set<PointcutPrimitive> SUPPORTED_PRIMITIVES = new HashSet<>();

    static {
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.EXECUTION);
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.ARGS);
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.REFERENCE);
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.THIS);
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.TARGET);
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.WITHIN);
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_ANNOTATION);
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_WITHIN);
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_ARGS);
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_TARGET);
    }

    public AspectJExpressionPointcut() {this(SUPPORTED_PRIMITIVES);}
    
    public AspectJExpressionPointcut(Set<PointcutPrimitive> supportedPrimitives) {
        pointcutParser  = PointcutParser.getPointcutParserSupportingSpecifiedPrimitivesAndUsingContextClassloaderForResolution(supportedPrimitives);
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    private void obtainPointcutExpression(){
        if(expression == null){
            throw new IllegalArgumentException("expression is null");
        }
        pointcutExpression = pointcutParser.parsePointcutExpression(expression);
    }



    /**
     * match class
     * @return
     */
    @Override
    public boolean matches(Class targetClass) {
        obtainPointcutExpression();
        return pointcutExpression.couldMatchJoinPointsInType(targetClass);
    }

    /**
     * match method
     * @param method
     * @return
     */
    @Override
    public boolean matches(Method method, Class targetClass) {
        obtainPointcutExpression();
        ShadowMatch shadowMatch = pointcutExpression.matchesMethodExecution(method);
        if(shadowMatch.alwaysMatches()){
            return true;
        }else if(shadowMatch.neverMatches()){
            return false;
        }
        return false;
    }

    @Override
    public ClassFilter getClassFilter() {

        return this;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }
}
