package com.myorg;

import com.myorg.constructs.TrafficGenerator;
import com.myorg.constructs.TrafficGeneratorProps;
import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.services.ec2.Vpc;
import software.amazon.awscdk.services.ec2.VpcLookupOptions;

public class TrafficStack extends Stack {
    private final TrafficGenerator trafficGenerator;

    public TrafficStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        TrafficGeneratorProps trafficGeneratorProps = TrafficGeneratorProps.builder()
                .tps(1)
                .url("https://ypkpbq4kkg.execute-api.us-west-2.amazonaws.com/prod/65d44f02")
                .vpc(Vpc.fromLookup(this, "VPC", VpcLookupOptions.builder().isDefault(true).build()))
                .build();

        this.trafficGenerator = new TrafficGenerator(this, "TestTraffic", trafficGeneratorProps);
    }
}
