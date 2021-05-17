package com.myorg.constructs;

import java.util.*;

import org.jetbrains.annotations.NotNull;
import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.services.ecs.*;

// A custom construct that defines an AWS Fargate service that has a task that runs in an ECS cluster located in a
// certain VPC.
public class TrafficGenerator extends Construct {
    private final Cluster cluster;
    private final TaskDefinition task;
    private final FargateService fargateService;

    // Generate traffic per second to a particular URL inside a VPC
    public TrafficGenerator(software.constructs.@NotNull Construct scope, @NotNull String id, TrafficGeneratorProps props) {
        super(scope, id);

        this.cluster = Cluster.Builder.create(this, "Cluster")
                .vpc(props.getVpc())
                .build();


        this.task = TaskDefinition.Builder.create(this, "PingerTask")
                .compatibility(Compatibility.FARGATE)
                .cpu("256")
                .memoryMiB("512")
                .build();

        ContainerDefinitionOptions container = ContainerDefinitionOptions.builder()
                .image(ContainerImage.fromAsset("pinger"))
                .environment(new HashMap<String, String>() {{
                    put("URL", props.getUrl());
                }})
                .build();

        this.task.addContainer("Pinger", container);

        this.fargateService = FargateService.Builder.create(this, "PingerService")
                .cluster(this.cluster)
                .taskDefinition(this.task)
                .desiredCount(props.getTps())
                .build();
    }
}
