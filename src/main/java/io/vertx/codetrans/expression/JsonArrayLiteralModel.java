package io.vertx.codetrans.expression;

import io.vertx.codegen.type.ClassKind;
import io.vertx.codegen.type.TypeInfo;
import io.vertx.codetrans.CodeBuilder;
import io.vertx.codetrans.CodeWriter;
import io.vertx.codetrans.Helper;
import io.vertx.codetrans.MethodSignature;

import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class JsonArrayLiteralModel extends ExpressionModel {

  private List<ExpressionModel> values;

  public JsonArrayLiteralModel(CodeBuilder builder) {
    this(builder, Collections.emptyList());
  }

  private JsonArrayLiteralModel(CodeBuilder builder, List<ExpressionModel> values) {
    super(builder);
    this.values = values;
  }

  public List<ExpressionModel> getValues() {
    return values;
  }

  @Override
  public ExpressionModel as(TypeInfo type) {
    if (type.getKind() != ClassKind.JSON_ARRAY) {
      throw new UnsupportedOperationException();
    }
    return this;
  }

  @Override
  public ExpressionModel onMethodInvocation(TypeInfo receiverType, MethodSignature method, TypeInfo returnType, List<ExpressionModel> argumentModels, List<TypeInfo> argumenTypes) {
    String methodName = method.getName();
    switch (methodName) {
      case "add":
        return new JsonArrayLiteralModel(builder, Helper.append(values, argumentModels.get(0)));
      case "addNull":
        return new JsonArrayLiteralModel(builder, Helper.append(values, new NullLiteralModel(builder)));
      default:
        throw new UnsupportedOperationException("Method " + method + " not yet implemented");
    }
  }

  @Override
  public void render(CodeWriter writer) {
    writer.renderJsonArray(this);
  }
}
