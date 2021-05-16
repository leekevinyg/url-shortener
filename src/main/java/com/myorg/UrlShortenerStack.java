package com.myorg;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awscdk.services.dynamodb.Attribute;
import software.amazon.awscdk.services.dynamodb.Table;
import software.amazon.awscdk.services.dynamodb.AttributeType;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.Runtime;
import software.amazon.awscdk.services.apigateway.LambdaRestApi;


public class UrlShortenerStack extends Stack {
    private final Table table;
    private final Function handler;
    private final LambdaRestApi api;

    public UrlShortenerStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public UrlShortenerStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);
        this.table = buildDynamoTable();
        this.handler = buildLambdaFunction();
        this.configureLambda();
        this.api = buildApiGateway();
    }

    private void configureLambda() {
        // grant lambda function permissions to ddb table and add the needed environment variable
        this.table.grantReadWriteData(this.handler);
        this.handler.addEnvironment("TABLE_NAME", this.table.getTableName());
    }

    private LambdaRestApi buildApiGateway() {
        return LambdaRestApi.Builder.create(this, "api")
                .handler(this.handler)
                .build();
    }

    private Function buildLambdaFunction() {
        return Function.Builder.create(this, "backend")
                .runtime(Runtime.NODEJS_10_X)
                .code(Code.fromAsset("lambda"))
                .handler("index.handler")
                .build();
    }

    private Table buildDynamoTable() {
        Attribute idAttribute = Attribute.builder()
                .name("id")
                .type(AttributeType.STRING)
                .build();

        return Table.Builder.create(this, "mapping-table")
                .partitionKey(idAttribute)
                .build();
    }
}
