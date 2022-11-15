package com.LubieKakao1212.neguns.expression;

import com.LubieKakao1212.qulib.util.joml.Vector3dUtil;
import com.fathzer.soft.javaluator.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Vector3d;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import java.util.function.BiFunction;

public class MultiTypeEvaluator extends AbstractEvaluator<Object> {

    //region Operators

    //Comparison operators
    private static final Operator EQUALS = new Operator("==", 2, Operator.Associativity.LEFT, 2);
    private static final Operator GREATER_THAN = new Operator(">", 2, Operator.Associativity.LEFT, 2);
    private static final Operator LESS_THAN = new Operator("<", 2, Operator.Associativity.LEFT, 2);
    private static final Operator GREATER_OR_EQUAL_THAN = new Operator(">=", 2, Operator.Associativity.LEFT, 2);
    private static final Operator LESS_OR_EQUAL_THAN = new Operator("<=", 2, Operator.Associativity.LEFT, 2);

    //Boolean operators
    private static final Operator AND = new Operator("&&", 2, Operator.Associativity.LEFT, 2);
    private static final Operator OR = new Operator("||", 2, Operator.Associativity.LEFT, 2);
    private static final Operator XOR = new Operator("^^", 2, Operator.Associativity.LEFT, 2);
    private static final Operator NOT = new Operator("!", 1, Operator.Associativity.RIGHT, 4);

    //Scalar only operators
    private static final Operator EXPONENT = new Operator("^", 2, Operator.Associativity.LEFT, 4);

    //Common scalar/vector operators
    /**
     *  Scalar * Vector
     *  Vector * Scalar
     *  Scalar * Scalar
     *  Vector * Vector //Per Component
     */
    private static final Operator MULTIPLY = new Operator("*", 2, Operator.Associativity.LEFT, 2);
    /**
     *  Scalar / Vector
     *  Vector / Scalar
     *  Scalar / Scalar
     *  Vector / Vector //Per Component
     */
    private static final Operator DIVIDE = new Operator("/", 2, Operator.Associativity.LEFT, 2);
    /**
     *  Vector + Vector
     *  Scalar + Scalar
     */
    private static final Operator ADD = new Operator("+", 2, Operator.Associativity.LEFT, 1);
    /**
     *  Vector - Vector
     *  Scalar - Scalar
     */
    private static final Operator SUBTRACT = new Operator("-", 2, Operator.Associativity.LEFT, 1);

    /**
     *
     */
    private static final Operator NEGATE = new Operator("-", 1, Operator.Associativity.RIGHT, 3);

    //Vector only operators
    /**
     * Vector.x
     * Vector.y
     * Vector.z
     * Vector.w // For quaternions
     */
    //TODO Do something about this not being a '.'
    private static final Operator COMPONENT = new Operator("->", 2, Operator.Associativity.LEFT, 10);
    //endregion

    //region Functions

    //Number functions
    public static final Function RANDOM = new Function("random", 2);

    //Vector construction
    public static final Function VEC3_CONSTRUCT = new Function("vec3", 2, 3);
    public static final Function QUATERNION_CONSTRUCT = new Function("quat", 4);

    //Vector operations
    public static final Function LENGTH = new Function("len", 1);
    public static final Function LENGTH_SQ = new Function("len2", 1);
    public static final Function NORMALIZE = new Function("norm", 1);
    public static final Function DOT = new Function("dot", 2);
    public static final Function CROSS = new Function("cross", 2);

    //Trigonometric functions
    public static final Function SIN = new Function("sin", 1);
    public static final Function COS = new Function("cos", 1);
    public static final Function TAN = new Function("tan", 1);

    public static final Function ASIN = new Function("asin", 1);
    public static final Function ACOS = new Function("acos", 1);
    public static final Function ATAN = new Function("atan", 1);
    public static final Function ATAN2 = new Function("atan2", 2);

    //Bulk actions
    public static final Function MIN = new Function("min", 1, 2147483647);
    public static final Function MAX = new Function("max", 1, 2147483647);
    public static final Function SUM = new Function("sum", 1, 2147483647);
    public static final Function AVERAGE = new Function("avg", 1, 2147483647);

    //Entity attribute getters
    public static final Function IS_LIVING = new Function("isLiving", 1);

    //endregion

    //region Constants
    public static final Constant PI = new Constant("pi");
    public static final Constant E = new Constant("e");

    public static final Constant UP = new Constant("up");
    public static final Constant DOWN = new Constant("down");
    public static final Constant SOUTH = new Constant("south");
    public static final Constant NORTH = new Constant("north");
    public static final Constant EAST = new Constant("east");
    public static final Constant WEST = new Constant("west");

    public static final Constant X = new Constant("x");
    public static final Constant Y = new Constant("y");
    public static final Constant Z = new Constant("z");
    public static final Constant W = new Constant("w");
    //endregion

    private static final Parameters PARAMETERS = new Parameters();

    static {
        PARAMETERS.addConstants(Arrays.asList(new Constant[] { PI, E, UP, DOWN, SOUTH, NORTH, EAST, WEST, X, Y, Z, W }));
        PARAMETERS.addOperators(Arrays.asList(new Operator[] {
                EQUALS, LESS_THAN, GREATER_THAN, LESS_OR_EQUAL_THAN, GREATER_OR_EQUAL_THAN,
                AND, OR, XOR, NOT,
                EXPONENT,
                MULTIPLY, DIVIDE, ADD, SUBTRACT, NEGATE,
                COMPONENT }));

        PARAMETERS.addFunctions(Arrays.asList(new Function[] {
                RANDOM,
                VEC3_CONSTRUCT, QUATERNION_CONSTRUCT,
                LENGTH, LENGTH_SQ, NORMALIZE, DOT, CROSS,
                SIN, COS, TAN, ASIN, ACOS, ATAN, ATAN2,
                MIN, MAX, SUM, AVERAGE,
                IS_LIVING}));
        PARAMETERS.addFunctionBracket(BracketPair.PARENTHESES);
        PARAMETERS.addExpressionBracket(BracketPair.PARENTHESES);
    }

    private Random random;

    public MultiTypeEvaluator() {
        super(PARAMETERS);
        random = new Random();
    }

    @Override
    protected Object evaluate(Constant constant, Object evaluationContext) {
        if(PI.equals(constant)) {
            return Math.PI;
        }
        else if (E.equals(constant)) {
            return Math.E;
        }

        else if (UP.equals(constant)) {
            return Vector3dUtil.up();
        }
        else if(DOWN.equals(constant)) {
            return Vector3dUtil.down();
        }
        else if(NORTH.equals(constant)) {
            return Vector3dUtil.north();
        }
        else if(SOUTH.equals(constant)) {
            return Vector3dUtil.south();
        }
        else if(EAST.equals(constant)) {
            return Vector3dUtil.east();
        }
        else if(WEST.equals((constant))) {
            return Vector3dUtil.west();
        }

        else if(X.equals(constant)) {
            return "x";
        }
        else if(Y.equals(constant)) {
            return "y";
        }
        else if(Z.equals(constant)) {
            return "z";
        }
        else if(W.equals(constant)) {
            return "w";
        }
        throw new EvaluationException("Invalid Constant");
    }

    @Override
    protected Object evaluate(Operator operator, Iterator<Object> operands, Object evaluationContext) {
        if(EQUALS.equals(operator)) {
            return operands.next().equals(operands.next());
        }
        else if(GREATER_THAN.equals(operator)) {
            return safeDouble(operands.next()) > safeDouble(operands.next());
        }
        else if(LESS_THAN.equals(operator)) {
            return safeDouble(operands.next()) < safeDouble(operands.next());
        }//TODO implement eqlo eqgr

        else if(AND.equals(operator)) {
            return safeBoolean(operands.next()) && safeBoolean(operands.next());
        }
        else if(OR.equals(operator)) {
            return safeBoolean(operands.next()) || safeBoolean(operands.next());
        }
        else if(XOR.equals(operator)) {
            return safeBoolean(operands.next()) ^ safeBoolean(operands.next());
        }
        else if(NOT.equals(operator)) {
            return !safeBoolean(operands.next());
        }

        else if(EXPONENT.equals(operator)) {
            return Math.pow(safeDouble(operands.next()), safeDouble(operands.next()));
        }

        else if(MULTIPLY.equals(operator)) {
            return validateNotNull(
                    vectorScalarTwoWayOperation(
                            operands.next(), operands.next(),
                            (a, b) -> a * b,
                            (a, b) -> b.mul(a),
                            Vector3d::mul),
                    "Invalid Multiplication Args");
        }
        else if(DIVIDE.equals(operator)) {
            return validateNotNull(
                    vectorScalarOperation(
                            operands.next(), operands.next(),
                            (a, b) -> a / b,
                            (a, b) -> null,
                            (a, b) -> a.div(b),
                            Vector3d::div),
                    "Invalid Division arguments");
        }
        else if(ADD.equals(operator)) {
            return validateNotNull(
                    vectorScalarTwoWayOperation(
                            operands.next(), operands.next(),
                            (a, b) -> a + b,
                            (a, b) -> null,
                            Vector3d::add),
                    "Invalid Addition Args");
        }
        else if(SUBTRACT.equals(operator)) {
            return validateNotNull(
                    vectorScalarTwoWayOperation(
                            operands.next(), operands.next(),
                            (a, b) -> a - b,
                            (a, b) -> null,
                            Vector3d::sub),
                    "Invalid Subtraction Args");
        }
        else if(NEGATE.equals(operator)) {
            Object arg = operands.next();
            if(arg instanceof Double) {
                return -(Double)arg;
            }
            if(arg instanceof Vector3d) {
                return ((Vector3d)arg).negate();
            }
        }
        else if(COMPONENT.equals(operator)) {
            Vector3d arg1 = safeVector(operands.next());
            String arg2 = safeString(operands.next());

            return switch(arg2) {
                case "x" -> arg1.x;
                case "y" -> arg1.y;
                case "z" -> arg1.z;
                default -> throw new EvaluationException("Invalid vector component");
            };
        }

        return super.evaluate(operator, operands, evaluationContext);
    }

    @Override
    protected Object evaluate(Function function, Iterator<Object> arguments, Object evaluationContext) {
        if(function.equals(RANDOM)) {
            return random.nextDouble(safeDouble(arguments.next()), safeDouble(arguments.next()));
        }

        else if(function.equals(IS_LIVING)) {
            return safeEntity(arguments.next()) instanceof LivingEntity;
        }
        return super.evaluate(function, arguments, evaluationContext);
    }

    @Override
    protected Object toValue(String s, Object o) {
        try {
            return Double.parseDouble(s);
        }
        catch(NumberFormatException e) {
            throw new EvaluationException(e.getMessage());
        }
    }

    public static Double safeDouble(Object arg) throws EvaluationException {
        if(!(arg instanceof Double))
            throw new EvaluationException("Expected Double, got: " + arg.getClass());
        return (Double) arg;
    }

    public static String safeString(Object arg) throws EvaluationException {
        if(!(arg instanceof String))
            throw new EvaluationException("Expected String, got: " + arg.getClass());
        return (String) arg;
    }

    public static Vector3d safeVector(Object arg) throws EvaluationException {
            if(!(arg instanceof Vector3d))
                throw new EvaluationException("Expected Vector3d, got: " + arg.getClass());
            return (Vector3d) arg;
    }

    public static Boolean safeBoolean(Object arg) throws EvaluationException {
        if(!(arg instanceof Boolean))
            throw new EvaluationException("Expected Boolean, got: " + arg.getClass());
        return (Boolean) arg;
    }

    public static Entity safeEntity(Object arg) throws EvaluationException {
        if(!(arg instanceof Entity))
            throw new EvaluationException("Expected Boolean, got: " + arg.getClass());
        return (Entity) arg;
    }

    public static Object validateNotNull(Object o, String message) {
        if(o == null) {
            throw new EvaluationException(message);
        }
        return o;
    }

    private Object vectorScalarTwoWayOperation(Object a1, Object a2, BiFunction<Double, Double, Object> SSAction, BiFunction<Double, Vector3d, Object> SVAction, BiFunction<Vector3d, Vector3d, Object> VVAction) {
        return vectorScalarOperation(a1, a2, SSAction, SVAction, (v, s) -> SVAction.apply(s, v), VVAction);
    }

    private Object vectorScalarOperation(Object a1, Object a2, BiFunction<Double, Double, Object> SSAction, BiFunction<Double, Vector3d, Object> SVAction, BiFunction<Vector3d, Double, Object> VSAction, BiFunction<Vector3d, Vector3d, Object> VVAction) {
        if(a1 instanceof Double)
        {
            if(a2 instanceof Double) {
                return SSAction.apply((Double)a1, (Double)a2);
            }
            else if(a2 instanceof Vector3d)
            {
                return SVAction.apply((Double) a1, (Vector3d) a2);
            }
            return null;
        }
        if(a1 instanceof Vector3d)
        {
            if(a2 instanceof Double) {
                return VSAction.apply((Vector3d) a1, (Double) a2);
            }
            else if(a2 instanceof Vector3d)
            {
                return VVAction.apply((Vector3d) a1, (Vector3d) a2);
            }
            return null;
        }
        return null;
    }
}
